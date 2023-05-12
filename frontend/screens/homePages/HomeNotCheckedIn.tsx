import {
  StyleSheet,
  Text,
  View,
  FlatList,
  Pressable,
  Image,
  Alert,
  ScrollView,
} from 'react-native';
import haversine from 'haversine';
import React, { useEffect, useState } from 'react';
import GroupItem from '../../components/HomePage/GroupItem';
import { Colors } from '../../constants/Colors';
import { Camera, CameraType } from 'expo-camera';
import { useNavigation, useTheme } from '@react-navigation/native';
import ListItem from '../../components/HomePage/ListItem';
import FAButton from '../../components/HomePage/FAButton';
import SearchBar from '../../components/HomePage/SearchBar';
import SearchIcon from '../../assets/SearchIcon.png';
import Icon from 'react-native-vector-icons/Ionicons';
import * as Font from 'expo-font';
import * as Location from 'expo-location';
import { BarCodeScanner } from 'expo-barcode-scanner';
import { useCheckedInStore } from '../../stores/CheckedInStore';
import { useLoginStore } from '../../stores/LoginStore';
import axios from 'axios';
import axiosConfig from '../../constants/axiosConfig';
import { useIsFocused } from '@react-navigation/native';
import { ActivityIndicator } from 'react-native';
import StyledButton from '../../components/HomePage/StyledButton';
const HomeNotCheckedIn = () => {
  const instanceToken = axiosConfig();
  const [isCamOpen, setIsCamOpen] = useState(false);
  const [type, setType] = useState(CameraType.back);
  const [permission, requestPermission] = Camera.useCameraPermissions();
  const [startCamera, setStartCamera] = React.useState(false);
  const [searchPhrase, setSearchPhrase] = useState('');
  const [clicked, setClicked] = useState(false);
  const navigation = useNavigation();
  const { phoneNumber, selectedCode } = useLoginStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      selectedCode: state.selectedCode,
    };
  });
  const [userList, setUserList] = useState([
    {
      id: 1,
      name: 'John Doe',
      status: 'At Federal Coffee Shop',
    },
    {
      id: 2,
      name: 'Jane Doe',
      status: 'At Bluejay Coffee Shop',
    },
    {
      id: 3,
      name: 'May Doe',
      status: 'At Federal Coffee Shop',
    },
    {
      id: 4,
      name: 'Jane anan',
      status: 'At Bluejay Coffee Shop',
    },
    {
      id: 5,
      name: 'anan baban',
      status: 'At Federal Coffee Shop',
    },
    {
      id: 6,
      name: 'eben Doe',
      status: 'At Bluejay Coffee Shop',
    },
  ]);
  const [filteredUserList, setFilteredUserList] = useState([
    {
      id: 1,
      name: 'John Doe',
      status: 'At Federal Coffee Shop',
    },
    {
      id: 2,
      name: 'Jane Doe',
      status: 'At Bluejay Coffee Shop',
    },
    {
      id: 3,
      name: 'May Doe',
      status: 'At Federal Coffee Shop',
    },
    {
      id: 4,
      name: 'Jane anan',
      status: 'At Bluejay Coffee Shop',
    },
    {
      id: 5,
      name: 'anan baban',
      status: 'At Federal Coffee Shop',
    },
    {
      id: 6,
      name: 'eben Doe',
      status: 'At Bluejay Coffee Shop',
    },
  ]);
  const [friendList, setFriendList] = useState([]);
  const { setIsCheckIn } = useCheckedInStore();
  const [isRequested, setIsRequested] = useState(false);
  const [locationVenue, setLocationVenue] = useState('');
  const [success, setSuccess] = useState(true);

  const dbUserName = selectedCode.dial_code.replace('+', '') + phoneNumber;
  //const getCurrentPositionAsync = async () => {
  //  console.log('entered c');

  //  try {
  //    const { status } = await Location.requestForegroundPermissionsAsync();
  //    if (status !== 'granted') {
  //      Alert.alert(
  //        'Permission denied',
  //        'You need to grant location permission to use this feature',
  //        [{ text: 'OK' }]
  //      );
  //      return;
  //    }
  //    const location = await Location.getCurrentPositionAsync();
  //    return location;
  //  } catch (e) {
  //    console.log(e);
  //  }
  //};
  const [userLocation, setUserLocation] = useState(null);
  const calculateDistance = (startLocation, endLocation) => {
    return haversine(startLocation, endLocation, { unit: 'meter' });
  };

  const handleScan = (res) => {
    //let locationVenue = '';
    //console.log(dbUserName);
    if (!isRequested) {
      setIsRequested(true);
      console.log('entered func');

      console.log('userLocation', userLocation);
      let { latitude, longitude } = userLocation?.coords;
      let locUser = { latitude, longitude };
      console.log('locUser', locUser);

      instanceToken
        .get(`/api/venues/${res}`)
        .then((result) => {
          console.log(result.data);
          if (result.data) {
            setLocationVenue(result.data.location);
          }
        })
        .catch((e) => {
          console.log(e);
          setIsRequested(false);
        });
      if (locationVenue === '') {
        setIsRequested(false);
        return;
      }
      let latitudeV = locationVenue.split(',')[0];
      let longitudeV = locationVenue.split(',')[1];

      if (!userLocation) {
        console.log('user location not found');

        setIsRequested(false);
        return;
      }
      const venueLocation = {
        latitude: latitudeV,
        longitude: longitudeV,
      };
      const distance = calculateDistance(locUser, venueLocation);
      console.log('venueLocation', venueLocation);

      console.log('distance', distance);
      if (distance < 10000) {
        Alert.alert('Out of range', 'You are not in the range of the venue', [
          { text: 'OK' },
        ]);
        setIsRequested(false);
        setStartCamera(false);
        return;
      }
      instanceToken
        .post(`/api/venues/${res}/checkIn/${dbUserName}`)
        .then((result) => {
          if (result.data) {
            setIsCheckIn(true);
            setIsRequested(false);
          }
        })
        .catch((e) => {
          console.log(e);
          setIsRequested(false);
        });
    }
  };

  const fetchUsers = () => {
    //console.log('fetching');
    instanceToken.get(`/api/customers`).then((res) => {
      //console.log(res.data);
      setFilteredUserList(
        res.data.filter((user) => user.username !== dbUserName)
      );
      setUserList(res.data.filter((user) => user.username !== dbUserName));
    });
    instanceToken.get(`/api/customers/${dbUserName}/friends`).then((res) => {
      //console.log(res.data);
      setFriendList(res.data.filter((user) => user.checkedInVenue !== null));
    });
  };
  const isFocused = useIsFocused();
  useEffect(() => {
    fetchUsers();
  }, [clicked, isFocused]);
  //useEffect(() => {
  //  instanceToken.get(`/api/customers/${dbUserName}/friends`).then((res) => {
  //    console.log(res.data);
  //    setFriendList(res.data.filter((user) => user.checkedInVenue !== null));
  //  });
  //}, []);
  const __startCamera = async () => {
    //let { status } = await Location.requestForegroundPermissionsAsync();
    //console.log(status);
    //if (status !== 'granted') {
    //  Alert.alert(
    //    'Permission denied',
    //    'You need to grant location permission to use this feature',
    //    [{ text: 'OK' }]
    //  );
    //  return;
    //}
    setSuccess(false);
    try {
      let location = await Location.getCurrentPositionAsync({});
      setUserLocation(location);
    } catch (error) {
      Alert.alert(
        'Permission denied',
        'You need to grant location permission to use this feature',
        [{ text: 'OK' }]
      );
      setSuccess(true);
      setIsCamOpen(false);

      console.log('Error getting current location:', error);
      return;
    }
    //setUserLocation(location);
    setSuccess(true);
    const { status } = await Camera.requestCameraPermissionsAsync();

    if (status === 'granted') {
      setStartCamera(true);
    } else {
      Alert.alert('Please grant access to camera from device settings!');
    }
  };

  useEffect(() => {
    //console.log('searchPhrase', searchPhrase);
    const filteredArray = userList.filter((user) => {
      return (
        user.name.toLowerCase().includes(searchPhrase.toLowerCase()) ||
        user.surname.toLowerCase().includes(searchPhrase.toLowerCase())
      );
    });
    setFilteredUserList(filteredArray);
  }, [searchPhrase]);

  const [fontsLoaded] = Font.useFonts({
    'Inter-Bold': require('../../assets/fonts/Inter/static/Inter-Bold.ttf'),
    'Inter-Medium': require('../../assets/fonts/Inter/static/Inter-Medium.ttf'),
    'Inter-Regular': require('../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });
  const data = [
    {
      text: 'Deneme 1',
    },
    {
      text: 'Deneme 2',
    },
    {
      text: 'Deneme 3',
    },
    {
      text: 'Deneme 4',
    },
    {
      text: 'Deneme 5',
    },
    {
      text: 'Deneme 6',
    },
    {
      text: 'Deneme 7',
    },
    {
      text: 'Deneme 8',
    },
  ];

  const handleUserPress = (id: Number) => {
    navigation.navigate(
      // @ts-ignore
      'ProfileDetails',
      { id: id }
    );
  };

  return !success ? (
    <View
      style={{
        backgroundColor: '#000',
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <Text
        style={{
          fontFamily: 'Inter-Regular',
          fontSize: 12,
          marginBottom: 20,
          color: '#fff',
        }}
      >
        {'Please wait while we check your location...'}
      </Text>
      <ActivityIndicator size='large' color='#EA34C9' />
      {/*<StyledButton
        buttonText='Cancel'
        onPress={() => {
          setStartCamera(false);
          setSuccess(true);
        }}
      />*/}
    </View>
  ) : !startCamera ? (
    <View style={styles.container}>
      <View
        style={{
          flex: 1,
          width: '100%',
        }}
      >
        <View
          style={{
            flexDirection: 'row',
            marginVertical: 20,
            alignItems: 'center',
          }}
        >
          {!clicked ? (
            <>
              <View style={styles.textContainer}>
                <Text style={styles.textStyle}>{'You are not Vybing!'}</Text>
              </View>
              <View style={{ marginLeft: 120 }}>
                <Pressable
                  style={({ pressed }) =>
                    pressed ? [styles.pressed] : [styles.buttonContainer]
                  }
                  android_ripple={{ color: '#000' }}
                  onPress={() => {
                    setClicked(true);
                  }}
                >
                  <Image source={SearchIcon} />
                </Pressable>
              </View>
            </>
          ) : (
            <SearchBar
              clicked={clicked}
              searchPhrase={searchPhrase}
              setClicked={setClicked}
              setSearchPhrase={setSearchPhrase}
            />
          )}
        </View>

        {clicked ? (
          <ScrollView style={{ height: '100%', marginBottom: 100 }}>
            {filteredUserList.map((user) => (
              <Pressable
                key={user.username}
                onPress={() => handleUserPress(user.username)}
              >
                <ListItem
                  key={user.username}
                  topText={user.name + ' ' + user.surname}
                  subText={
                    user.checkedInVenue
                      ? user.checkedInVenue.name
                      : 'Not checked in'
                  }
                />
              </Pressable>
            ))}
          </ScrollView>
        ) : (
          <>
            <FlatList
              data={data}
              renderItem={({ item }) => <GroupItem text={item.text} />}
              horizontal={true}
              style={{ flexGrow: 0, marginBottom: 100 }}
            />
            {friendList.length > 0 ? (
              <>
                <Text style={[styles.textStyle]}>
                  {'Friends currently Vybing'}
                </Text>

                {friendList.map((friend) => (
                  <Pressable
                    key={friend.username}
                    onPress={() => handleUserPress(friend.username)}
                  >
                    <ListItem
                      key={friend.username}
                      topText={friend.name + ' ' + friend.surname}
                      subText={friend.checkedInVenue.name}
                    />
                  </Pressable>
                ))}
              </>
            ) : (
              <Text style={[styles.textStyle, { marginBottom: 20 }]}>
                {'No friends currently Vybing'}
              </Text>
            )}
          </>
        )}
      </View>

      {!clicked && (
        <FAButton
          style={{ zIndex: 100, bottom: 100, position: 'absolute' }}
          onPress={__startCamera}
        />
      )}
    </View>
  ) : (
    <View
      style={{
        flex: 1,
        width: '100%',
      }}
    >
      <Camera
        style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}
        type={CameraType.back}
        barCodeScannerSettings={{
          barCodeTypes: [BarCodeScanner.Constants.BarCodeType.qr],
        }}
        onBarCodeScanned={(result: any) => {
          //TODO: fetch data if is valid
          handleScan(result.data);

          //setIsCheckIn(true);
        }}
      >
        <Pressable
          onPress={() => setStartCamera(false)}
          style={({ pressed }) =>
            pressed ? [styles.backButtonPressed] : [styles.backButton]
          }
        >
          <Icon name='chevron-back' color='#fff' size={40} />
        </Pressable>
        <View
          style={{
            width: 250,
            height: 250,
            borderWidth: 4,
            borderColor: '#fff',
            borderRadius: 35,
            marginBottom: 10,
          }}
        ></View>
        <Text style={styles.textStyle}>{'Scan QR'}</Text>
      </Camera>
    </View>
  );
};
const styles = StyleSheet.create({
  flatList: {
    //flex: 1,
    justifyContent: 'space-between',
  },
  container: {
    flex: 1,
    width: '100%',
    alignItems: 'center',
  },
  textStyle: {
    fontFamily: 'Inter-Bold',
    fontSize: 24,
    color: '#fff',
    //lineHeight: 20,

    paddingLeft: 8,
  },
  textContainer: {
    flexDirection: 'row',
    justifyContent: 'flex-start',
  },
  buttonContainer: {
    width: 20,
    height: 20,
    //backgroundColor: Colors.red.error,
    borderRadius: 100,
    justifyContent: 'center',
    alignItems: 'center',
    //marginTop: 20,
  },
  backButton: {
    position: 'absolute',
    width: 60,
    height: 60,
    marginTop: 10,
    marginLeft: 10,
    top: 10,
    left: 10,
  },
  backButtonPressed: {
    position: 'absolute',
    width: 60,
    height: 60,
    marginTop: 10,
    marginLeft: 10,
    top: 10,
    left: 10,
    opacity: 0.5,
  },
  pressed: {
    width: 20,
    height: 20,
    //backgroundColor: Colors.red.error,
    borderRadius: 100,
    justifyContent: 'center',
    alignItems: 'center',
    //marginTop: 20,
    opacity: 0.5,
  },
});
export default HomeNotCheckedIn;
