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
  ActivityIndicator,
  RefreshControl,
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
import SwitchSelector from 'react-native-switch-selector-fix';
import StyledButton from '../../components/HomePage/StyledButton';
import CoinIcon from '../../assets/coin.png';
import InputSpinner from 'react-native-input-spinner';
import { useLoginStore } from '../../stores/LoginStore';
import axios from 'axios';
import axiosConfig from '../../constants/axiosConfig';

const HomeCheckedIn = () => {
  const instanceToken = axiosConfig();
  const [checkedInVenue, setCheckedInVenue] = useState({});
  const [songQueue, setSongQueue] = useState([]);
  const [songListFiltered, setSongListFiltered] = useState([]);
  const { phoneNumber, selectedCode } = useLoginStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      selectedCode: state.selectedCode,
    };
  });

  const [refreshing, setRefreshing] = React.useState(false);

  const onRefresh = React.useCallback(() => {
    setRefreshing(true);
    setTimeout(() => {
      fetchQueue();
      setRefreshing(false);
    }, 2000);
  }, []);

  const dbUserName = selectedCode.dial_code.replace('+', '') + phoneNumber;
  const [checkedInVenueId, setCheckedInVenueId] = useState();
  useEffect(() => {
    instanceToken
      .get(`/api/customers/${dbUserName}`)
      .then((res) => {
        //console.log('anan', res.data);
        setCheckedInVenue(res.data.checkedInVenue);
        setCheckedInVenueId(res.data.checkedInVenue.id);
        //fetchQueue();
        //fetchSongs();
        console.log('checkedInVenue', res.data.checkedInVenue.id);
      })
      .catch((e) => {
        console.log(e.message, 'get venue');
      });
  }, []);
  //useEffect(() => {
  //  instanceToken.get(`/api/customers`).then((res) => {
  //    console.log(res.data);
  //  });
  //}, []);
  const [showBox, setShowBox] = useState(true);
  const showConfirmDialog = () => {
    return Alert.alert('Are your sure?', 'Are you sure you want to checkout?', [
      // The "Yes" button
      // The "No" button
      // Does nothing but dismiss the dialog when tapped
      {
        text: 'No',
      },
      {
        text: 'Yes',
        onPress: () => {
          instanceToken
            .post(`api/venues/${checkedInVenueId}/checkOut/${dbUserName}`)
            .then((res) => {
              if (res.data) {
                setShowBox(false);
                setIsCheckIn(false);
              }
            })
            .catch((e) => console.log(e, 'checlkout'));
        },
      },
    ]);
  };

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
  const [requestType, setRequestType] = useState('default');
  const [coinBalance, setCoinBalance] = useState(1000);
  const [success, setSuccess] = useState(true);
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
  const [songList, setSongList] = useState([
    {
      id: 1,
      name: 'Song 1',
      artist: 'Artist 1',
    },
    {
      id: 2,
      name: 'Song 2',
      artist: 'Artist 2',
    },
    {
      id: 3,
      name: 'Song 3',
      artist: 'Artist 3',
    },
    {
      id: 4,
      name: 'Song 4',
      artist: 'Artist 4',
    },
    {
      id: 5,
      name: 'Song 5',
      artist: 'Artist 5',
    },
    {
      id: 6,
      name: 'Song 6',
      artist: 'Artist 6',
    },
  ]);
  const [selectedSong, setSelectedSong] = useState({});
  const options = [
    {
      label: 'Default Request',
      value: 'default',
      testID: 'switch-one',
      accessibilityLabel: 'switch-one',
    },
    {
      label: 'Enhanced Request',
      value: 'enhanced',
      testID: 'switch-two',
      accessibilityLabel: 'switch-two',
    },
  ];
  const [amount, setAmount] = useState(200);
  const [addingSongToQueue, setAddingSongToQueue] = useState(false);
  const [filteredUserList, setFilteredUserList] = useState([]);
  const handleUserPress = (id: Number) => {
    navigation.navigate(
      // @ts-ignore
      'ProfileDetails',
      { id: id }
    );
  };
  const handleSongPress = (song: any) => {
    setAddingSongToQueue(true);
    console.log('id', song.id);
    setSelectedSong(song);
  };
  const navigation = useNavigation();
  const fetchUsers = () => {
    //console.log('fetching');
    instanceToken
      .get(`/api/customers`)
      .then((res) => {
        //console.log(res.data);
        setFilteredUserList(
          res.data.filter((user) => user.username !== dbUserName)
        );
        setUserList(res.data.filter((user) => user.username !== dbUserName));
      })
      .catch((e) => console.log(e, 'fetchUsers'));
  };
  const fetchQueue = () => {
    instanceToken
      .get(`/api/venues/${checkedInVenueId}/nextSongs`)
      .then((res) => {
        //console.log(res.data);
        setSongQueue(res.data);
      })
      .catch((e) => console.log(e, 'fetchQueue'));
  };

  useEffect(() => {
    //console.log('searchPhrase', searchPhrase);
    setSuccess(false);
    const filteredArray = songList.filter((song) => {
      return (
        song.artist.toLowerCase().includes(searchPhrase.toLowerCase()) ||
        song.name.toLowerCase().includes(searchPhrase.toLowerCase())
      );
    });
    setSongListFiltered(filteredArray);
    setSuccess(true);
  }, [searchPhrase]);

  const fetchSongs = () => {
    setSuccess(false);
    instanceToken
      .get(`/api/venues/${checkedInVenueId}/defaultPlaylist/songs`)
      .then((res) => {
        //console.log(res.data);
        setSongList(res.data);
        setSongListFiltered(res.data);
        setSuccess(true);
      })
      .catch((e) => {
        console.log(e);
        setSuccess(true);
      });
  };

  useEffect(() => {
    if (checkedInVenueId !== '') {
      fetchSongs();
      fetchQueue();
      fetchUsers();
    }
    //fetchQueue();
  }, [checkedInVenueId]);
  useEffect(() => {
    const filteredArray = userList.filter((user) => {
      return (
        user.name.toLowerCase().includes(searchPhraseHome.toLowerCase()) ||
        user.surname.toLowerCase().includes(searchPhraseHome.toLowerCase())
      );
    });
    setFilteredUserList(filteredArray);
  }, [searchPhraseHome]);
  const __addSongToQueue = async () => {
    fetchSongs();
    setAddSong(true);
  };

  const __checkout = () => {
    setModalVisible(true);
  };

  const checkoutFromVenue = async () => {
    //setModalVisible(false);
    setIsCheckIn(false);
  };

  const handleDefaultSongRequest = () => {
    console.log('Default song request', selectedSong);

    let data = {
      playlistId: 1,
      songId: selectedSong.id,
      requestedByUsername: dbUserName,
      requestedInVenueId: checkedInVenueId,
      requestDate: Date.now(),
      coinCost: 100,
    };

    instanceToken
      .post(`/api/songRequests`, data)
      .then((res) => {
        console.log(res.data);
        fetchQueue();
      })
      .catch((e) => {
        console.log(e);
      });

    setAddingSongToQueue(false);
  };

  const handleEnhancedSongRequest = () => {
    console.log('Enhanced song request', amount);
    let data = {
      playlistId: 1,
      songId: selectedSong.id,
      requestedByUsername: dbUserName,
      requestedInVenueId: checkedInVenueId,
      requestDate: Date.now(),
      coinCost: amount,
    };

    instanceToken
      .post(`/api/songRequests`, data)
      .then((res) => {
        console.log(res.data);
        fetchQueue();
      })
      .catch((e) => {
        console.log(e);
      });

    setAddingSongToQueue(false);
  };

  const [fontsLoaded] = Font.useFonts({
    'Inter-Bold': require('../../assets/fonts/Inter/static/Inter-Bold.ttf'),
    'Inter-Medium': require('../../assets/fonts/Inter/static/Inter-Medium.ttf'),
    'Inter-Regular': require('../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });
  if (!isCheckIn) {
    return null;
  }

  return !addSong ? (
    <>
      {/*<Modal
        animationType='fade'
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => setModalVisible(!modalVisible)}
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
                onPress={() => setIsCheckIn(false)}
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
                <Text style={styles.modalTextStyle}>{'I changed my mind'}</Text>
              </Pressable>
            </View>
          </View>
        </View>
      </Modal>*/}
      {showBox && <View></View>}
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
                    {checkedInVenue.name}
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
            <View onLayout={fetchQueue} style={{ flex: 1 }}>
              <Text
                style={[styles.textStyle, { marginBottom: 20, fontSize: 23 }]}
              >
                {'Current Song Queue'}
              </Text>
              <ScrollView
                style={{ height: '100%', marginBottom: 100 }}
                refreshControl={
                  <RefreshControl
                    tintColor={'#EA34C9'}
                    refreshing={refreshing}
                    onRefresh={onRefresh}
                  />
                }
              >
                {songQueue.map((song) => (
                  <Pressable key={song.id}>
                    <ListItem
                      key={song.id}
                      profilePic={song.link}
                      topText={song.name}
                      subText={song.artist ? song.artist : 'No Artist'}
                    />
                  </Pressable>
                ))}
              </ScrollView>
            </View>
          ) : (
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
              onPress={() => showConfirmDialog()}
            />
          </>
        )}
      </View>
    </>
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
        visible={addingSongToQueue}
      >
        <View style={styles.centeredView}>
          <View
            style={
              requestType === 'default'
                ? styles.modalAddToQueueView
                : styles.modalAddToQueueView
            }
          >
            <Text
              style={{
                fontFamily: 'Inter-Bold',
                fontSize: 24,
                textAlign: 'center',
                color: 'white',
              }}
            >
              Add {selectedSong.name} to Queue
            </Text>
            <View
              style={{
                flex: 1,
                justifyContent: 'space-between',
                alignItems: 'center',
              }}
            >
              <View style={{ marginVertical: 10, width: 275 }}>
                <SwitchSelector
                  initial={requestType === 'default' ? 0 : 1}
                  //@ts-ignore
                  onPress={(value) => setRequestType(value)}
                  buttonColor={Colors.purple.dark}
                  borderColor={Colors.purple.dark}
                  options={options}
                  textStyle={{ fontFamily: 'Inter-Regular' }}
                />
              </View>
              {requestType === 'default' ? (
                <>
                  <Text style={styles.modalTextStylePrimary}>
                    {
                      'You may wait for a while to listen it... But you can always choose Enhanced Request!'
                    }
                  </Text>
                  <Pressable
                    style={[styles.button, styles.buttonPrimary]}
                    onPress={handleDefaultSongRequest}
                  >
                    <Text
                      style={{
                        fontFamily: 'Inter-Regular',
                        color: 'white',
                        fontSize: 20,
                      }}
                    >
                      Default Request (100 Coins)
                    </Text>
                  </Pressable>
                </>
              ) : (
                <>
                  <Text style={styles.modalTextStylePrimary}>
                    {
                      'Best way to listen it sooner! The more you tip, the quicker you listen!'
                    }
                  </Text>

                  <View style={{ width: '65%', alignItems: 'center' }}>
                    <InputSpinner
                      max={10000}
                      min={200}
                      step={50}
                      selectionColor='#ffffff'
                      inputStyle={{ fontSize: 20 }}
                      placeholder='5'
                      color={Colors.purple.primary}
                      background={Colors.purple.lighter}
                      onChange={(val) => setAmount(val)}
                      editable={false}
                    />
                  </View>
                  <Pressable
                    style={[styles.button, styles.buttonPrimary]}
                    onPress={handleEnhancedSongRequest}
                  >
                    <Text
                      style={{
                        fontFamily: 'Inter-Regular',
                        color: 'white',
                        fontSize: 20,
                      }}
                    >
                      Enhanced Request
                    </Text>
                  </Pressable>
                </>
              )}
              <Text style={styles.modalTextStylePrimary}>
                {'Coin Balance: ' + coinBalance}
              </Text>
              <Pressable onPress={() => setAddingSongToQueue(false)}>
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

      {success ? (
        <>
          <SearchBar
            searchPhrase={searchPhrase}
            clicked={clicked}
            setClicked={setClicked}
            setSearchPhrase={setSearchPhrase}
          />
          <ScrollView style={{ height: '100%', marginBottom: 100 }}>
            {songListFiltered.length === 0 ? (
              <Text
                style={{
                  fontFamily: 'Inter-Regular',
                  fontSize: 18,
                  color: '#7C757E',
                  paddingLeft: 8,
                }}
              >
                {'No songs found'}
              </Text>
            ) : (
              songListFiltered.map((song) => (
                <Pressable key={song.id} onPress={() => handleSongPress(song)}>
                  <ListItem
                    key={song.id}
                    profilePic={song.link}
                    topText={song.name}
                    subText={song.artist ? song.artist : 'No Artist'}
                  />
                </Pressable>
              ))
            )}
          </ScrollView>
        </>
      ) : (
        <View
          style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}
        >
          <Text
            style={{
              fontFamily: 'Inter-Regular',
              fontSize: 20,
              marginBottom: 20,
              color: '#fff',
            }}
          >
            {'Fetcing Songs...'}
          </Text>
          <ActivityIndicator size='large' color='#EA34C9' />
        </View>
      )}
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
    width: '98%',
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
  modalAddToQueueView: {
    margin: 20,
    width: '98%',
    height: 400,
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
  buttonPrimary: {
    backgroundColor: Colors.purple.primary,
  },
  modalTextStyle: {
    color: '#979c9e',
    fontFamily: 'Inter-Regular',
    fontSize: 17,
    textAlign: 'center',
  },
  modalTextStylePrimary: {
    color: '#ffff',
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
