import {
  StyleSheet,
  Text,
  View,
  FlatList,
  Pressable,
  Image,
  Alert,
  ScrollView,
  Modal,
} from 'react-native';
import React, { useEffect, useState } from 'react';
import GroupItem from '../../components/HomePage/GroupItem';
import { Colors } from '../../constants/Colors';
import { Camera, CameraType } from 'expo-camera';
import FAAddSongToQueue from '../../components/HomePage/FAAddSongToQueue';
import SearchBar from '../../components/HomePage/SearchBar';
import { useNavigation, useTheme } from '@react-navigation/native';
import ListItem from '../../components/HomePage/ListItem';
import FAButton from '../../components/HomePage/FAButton';
import SearchIcon from '../../assets/SearchIcon.png';
import Icon from 'react-native-vector-icons/Ionicons';
import EvilIcon from 'react-native-vector-icons/EvilIcons';
import * as Font from 'expo-font';
import { BarCodeScanner } from 'expo-barcode-scanner';
import FABCheckout from '../../components/HomePage/FABCheckout';
import { useCheckedInStore } from '../../stores/CheckedInStore';

const HomeCheckedIn = () => {
  const [isCamOpen, setIsCamOpen] = useState(false);
  const [type, setType] = useState(CameraType.back);
  const [permission, requestPermission] = Camera.useCameraPermissions();
  const [addSong, setAddSong] = React.useState(false);
  const [searchPhrase, setSearchPhrase] = useState('');
  const [clicked, setClicked] = useState(false);
  const [searchPhraseHome, setSearchPhraseHome] = useState('');
  const [clickedHome, setClickedHome] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const { isCheckIn, setIsCheckIn } = useCheckedInStore();
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
  const handleUserPress = (id: Number) => {
    navigation.navigate(
      // @ts-ignore
      'ProfileDetails',
      { id: id }
    );
  }
  const navigation = useNavigation();
  useEffect(() => { }, []);
  const __addSongToQueue = async () => {
    setAddSong(true);
  };

  const __checkout = async () => {
    setModalVisible(true);
  };

  const checkoutFromVenue = () => {
    setIsCheckIn(false);
    setModalVisible(false);
  };

  const [fontsLoaded] = Font.useFonts({
    'Inter-Bold': require('../../assets/fonts/Inter/static/Inter-Bold.ttf'),
    'Inter-Medium': require('../../assets/fonts/Inter/static/Inter-Medium.ttf'),
    'Inter-Regular': require('../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });

  return !addSong ? (
    <View style={styles.container}>
      <View
        style={{
          flex: 1,
          width: '100%',
        }}
      >
        <Modal
          animationType='fade'
          transparent={true}
          visible={modalVisible}
          onRequestClose={() => {
            Alert.alert('Modal has been closed.');
            setModalVisible(!modalVisible);
          }}
        >
          <View style={styles.centeredView}>
            <View style={styles.modalView}>
              <Text style={styles.modalText}>Checking Out</Text>
              <View style={{ flex: 1, justifyContent: 'space-around' }}>
                <Text style={styles.modalTextStyle}>
                  {'Are you sure you want to checkout from this location?'}
                </Text>
                <Pressable
                  style={[styles.button, styles.buttonClose]}
                  onPress={checkoutFromVenue}
                >
                  <Text
                    style={{
                      fontFamily: 'Inter-Regular',
                      color: 'white',
                      fontSize: 20,
                    }}
                  >
                    Yes I'm Sure
                  </Text>
                </Pressable>
                <Pressable onPress={() => setModalVisible(false)}>
                  <Text style={styles.modalTextStyle}>
                    {'I changed my mind'}
                  </Text>
                </Pressable>
              </View>
            </View>
          </View>
        </Modal>
        <View
          style={{
            flexDirection: 'row',
            marginVertical: 20,
            alignItems: 'center',
          }}
        >
          {!clickedHome ? (
            <View>
              <View style={styles.textContainer}>
                <Text style={styles.textStyle}>{'You are Now Vybing!'}</Text>
                <View style={{ marginLeft: 90 }}>
                  <Pressable
                    style={({ pressed }) =>
                      pressed ? [styles.pressed] : [styles.buttonContainer]
                    }
                    android_ripple={{ color: '#000' }}
                    onPress={() => setClickedHome(true)}
                  >
                    <Image source={SearchIcon} />
                  </Pressable>
                </View>
              </View>
              <View style={styles.textContainer}>
                <View style={{ paddingLeft: 8, justifyContent: 'center' }}>
                  <EvilIcon name='location' color={'#7c757e'} size={30} />
                </View>
                <Text style={styles.locationTextStyle}>
                  {'Location will be displayed here'}
                </Text>
              </View>
            </View>
          ) : (
            <SearchBar
              setClicked={setClickedHome}
              clicked={clickedHome}
              searchPhrase={searchPhraseHome}
              setSearchPhrase={setSearchPhraseHome}
            />
          )}
        </View>
        {!clickedHome ? (
          <>
            <Text style={[styles.textStyle, { marginBottom: 20, fontSize: 23 }]}>
              {'Current Song Queue'}
            </Text>
            <ScrollView style={{ height: '100%', marginBottom: 100 }}>
              <ListItem topText={'Song Name'} subText={'Artist Name'} />
              <ListItem topText={'Song Name'} subText={'Artist Name'} />
              <ListItem topText={'Song Name'} subText={'Artist Name'} />
              <ListItem topText={'Song Name'} subText={'Artist Name'} />
              <ListItem topText={'Song Name'} subText={'Artist Name'} />
              <ListItem topText={'Song Name'} subText={'Artist Name'} />
              <ListItem topText={'Song Name'} subText={'Artist Name'} />
              <ListItem topText={'Song Name'} subText={'Artist Name'} />
            </ScrollView>
          </>
        ) : (
          <ScrollView style={{ height: '100%', marginBottom: 100 }}>
            {userList.map((user) => (
              <Pressable
                key={user.id}
                onPress={() => handleUserPress(user.id)}>
                <ListItem
                  key={user.id}
                  topText={user.name}
                  subText={user.status}
                />
              </Pressable>
            ))}
          </ScrollView>
        )}
      </View>
      {!clickedHome && (
        <>
          <FAAddSongToQueue
            buttonText={'Add Song To Queue'}
            style={{ zIndex: 100, bottom: 180, position: 'absolute' }}
            onPress={__addSongToQueue}
          />
          <FABCheckout
            buttonText={'Checkout'}
            style={{ zIndex: 100, bottom: 100, position: 'absolute' }}
            onPress={__checkout}
          />
        </>
      )}
    </View>
  ) : (
    <View
      style={{
        flex: 1,
        width: '100%',
      }}
    >
      <Modal
        animationType='fade'
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          Alert.alert('Modal has been closed.');
          setModalVisible(!modalVisible);
        }}
      >
        <View style={styles.centeredView}>
          <View style={styles.modalView}>
            <Text style={styles.modalText}>Checking Out</Text>
            <View style={{ flex: 1, justifyContent: 'space-around' }}>
              <Text style={styles.modalTextStyle}>
                {'Are you sure you want to checkout from this location?'}
              </Text>
              <Pressable
                style={[styles.button, styles.buttonClose]}
                onPress={() => setModalVisible(!modalVisible)}
              >
                <Text
                  style={{
                    fontFamily: 'Inter-Regular',
                    color: 'white',
                    fontSize: 20,
                  }}
                >
                  Yes I'm Sure
                </Text>
              </Pressable>
              <Pressable onPress={() => setModalVisible(!modalVisible)}>
                <Text style={styles.modalTextStyle}>{'I changed my mind'}</Text>
              </Pressable>
            </View>
          </View>
        </View>
      </Modal>
      <View style={{ alignItems: 'center', flexDirection: 'row' }}>
        <Pressable
          onPress={() => setAddSong(false)}
          style={({ pressed }) =>
            pressed ? [styles.backButtonPressed] : [styles.backButton]
          }
        >
          <Icon name='chevron-back' color='#fff' size={40} />
        </Pressable>
        <View>
          <Text style={[styles.textStyle, { fontSize: 23 }]}>
            {'Add Song To Queue'}
          </Text>
        </View>
      </View>
      <SearchBar
        searchPhrase={searchPhrase}
        clicked={clicked}
        setClicked={setClicked}
        setSearchPhrase={setSearchPhrase}
      />
      <ScrollView style={{ height: '100%', marginBottom: 100 }}>
        <ListItem topText={'Song Name'} subText={'Artist Name'} />
        <ListItem topText={'Song Name'} subText={'Artist Name'} />
        <ListItem topText={'Song Name'} subText={'Artist Name'} />
        <ListItem topText={'Song Name'} subText={'Artist Name'} />
        <ListItem topText={'Song Name'} subText={'Artist Name'} />
        <ListItem topText={'Song Name'} subText={'Artist Name'} />
        <ListItem topText={'Song Name'} subText={'Artist Name'} />
        <ListItem topText={'Song Name'} subText={'Artist Name'} />
      </ScrollView>
      <View style={{ alignItems: 'center' }}>
        <FABCheckout
          buttonText={'Checkout'}
          style={{ zIndex: 100, bottom: 100, position: 'absolute' }}
          onPress={__checkout}
        />
      </View>
    </View>
  );
};
const styles = StyleSheet.create({
  flatList: {
    //flex: 1,
    justifyContent: 'space-between',
  },
  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 22,
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
  locationTextStyle: {
    fontFamily: 'Inter-Regular',
    fontSize: 18,
    color: '#7C757E',
    paddingLeft: 8,
  },
  textContainer: {
    flexDirection: 'row',
    width: '100%',
    //justifyContent: 'flex-start',
  },
  buttonContainer: {
    width: 30,
    height: 30,
    //backgroundColor: Colors.red.error,
    borderRadius: 100,
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: 20,
  },
  backButton: {
    //position: 'absolute',
    width: 60,
    height: 60,
    justifyContent: 'center',
    //marginTop: 10,
    //marginLeft: 10,
    //marginBottom: 20,
    //top: 10,
    //left: 10,
  },
  backButtonPressed: {
    //position: 'absolute',
    width: 60,
    height: 60,
    justifyContent: 'center',
    //marginTop: 10,
    //marginLeft: 10,
    //marginBottom: 20,
    //top: 10,
    //left: 10,
    opacity: 0.5,
  },
  pressed: {
    width: 20,
    height: 20,
    //backgroundColor: Colors.red.error,
    borderRadius: 100,
    //justifyContent: 'center',
    //alignItems: 'center',
    //marginTop: 20,
    opacity: 0.5,
  },
  modalView: {
    margin: 20,
    width: 400,
    height: 300,
    backgroundColor: '#202325',
    borderRadius: 20,
    padding: 35,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  button: {
    borderRadius: 20,
    padding: 10,
    elevation: 2,
    alignItems: 'center',
  },

  buttonClose: {
    backgroundColor: Colors.red.error,
  },
  modalTextStyle: {
    color: '#979c9e',
    fontFamily: 'Inter-Regular',
    fontSize: 17,
    textAlign: 'center',
  },
  modalText: {
    marginBottom: 15,
    fontFamily: 'Inter-Bold',
    fontSize: 24,
    textAlign: 'center',
    color: 'white',
  },
});
export default HomeCheckedIn;
