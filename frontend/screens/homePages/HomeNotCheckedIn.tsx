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
import { BarCodeScanner } from 'expo-barcode-scanner';
import { useCheckedInStore } from '../../stores/CheckedInStore';
import { useLoginStore } from '../../stores/LoginStore';
import axios from 'axios';

const HomeNotCheckedIn = () => {
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
      name: 'John Doe',
      status: 'At Federal Coffee Shop',
    },
    {
      id: 4,
      name: 'Jane Doe',
      status: 'At Bluejay Coffee Shop',
    },
    {
      id: 5,
      name: 'John Doe',
      status: 'At Federal Coffee Shop',
    },
    {
      id: 6,
      name: 'Jane Doe',
      status: 'At Bluejay Coffee Shop',
    },
  ]);
  const { setIsCheckIn } = useCheckedInStore();
  const [isRequested, setIsRequested] = useState(false);
  const dbUserName = selectedCode.dial_code.replace('+', '') + phoneNumber;
  const handleScan = (res) => {
    console.log(dbUserName);
    if (!isRequested) {
      setIsRequested(true);
      axios
        .post(
          `http://192.168.1.127:8080/api/venues/${res}/checkIn/${dbUserName}`
        )
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

  useEffect(() => {}, []);
  const __startCamera = async () => {
    const { status } = await Camera.requestCameraPermissionsAsync();

    if (status === 'granted') {
      setStartCamera(true);
    } else {
      Alert.alert('Please grant access to camera from device settings!');
    }
  };

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

  return !startCamera ? (
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
            {userList.map((user) => (
              <Pressable key={user.id} onPress={() => handleUserPress(user.id)}>
                <ListItem
                  key={user.id}
                  topText={user.name}
                  subText={user.status}
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
            <Text style={[styles.textStyle, { marginBottom: 20 }]}>
              {'Friends currently Vybing'}
            </Text>
            <ListItem topText={'Friend name'} subText={'Location'} />
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
