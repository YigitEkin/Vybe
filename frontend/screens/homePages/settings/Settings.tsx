import React, { useEffect, useState } from 'react';
import {
  Text,
  View,
  StyleSheet,
  Image,
  Pressable,
  ScrollView,
  Modal,
  Button,
} from 'react-native';
import StyledButton from '../../../components/HomePage/StyledButton';
import * as ImagePicker from 'expo-image-picker';
import { Colors } from '../../../constants/Colors';
import * as Font from 'expo-font';
import { launchCamera, launchImageLibrary } from 'react-native-image-picker';
// @ts-ignore
import CoinIcon from '../../../assets/coin.png';
import { useNavigation } from '@react-navigation/native';
import { useLoginStore } from '../../../stores/LoginStore';
import { useIsFocused } from '@react-navigation/native';
import axiosConfig from '../../../constants/axiosConfig';
import avatarPlaceholder from '../../../assets/avatarPlaceholder.png';
import { Toast } from 'react-native-toast-message/lib/src/Toast';

type editSectionArea = {
  name: string;
  value: any;
  editability: boolean;
  borderBottomless?: boolean;
  marginTop?: number;
  notificationCount?: number;
  onPress?: () => void;
};

const editSections: editSectionArea[] = [
  {
    name: 'First Name',
    value: 'First Name',
    editability: false,
    marginTop: 20,
  },
  {
    name: 'Last Name',
    value: 'Last Name',
    editability: false,
  },
  //{
  //  name: 'Location',
  //  editability: true,
  //},
  {
    name: 'Notfications',
    value: 'Notifications',
    editability: false,
    borderBottomless: true,
    notificationCount: 0,
    onPress: () => {},
  },
];
const EditProfileSection = (
  section: editSectionArea,
  notificationCount: number
) => {
  const [fontsLoaded] = Font.useFonts({
    'Inter-Regular': require('../../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });
  const navigation = useNavigation();

  return fontsLoaded ? (
    <View
      style={[
        styles.editSectionContainer,
        {
          borderBottomColor: section.borderBottomless ? undefined : '#202325',
          marginTop: section.marginTop,
        },
      ]}
    >
      <Text style={styles.sectionName}>{section.name}:</Text>
      <Text style={styles.sectionName}>{section.value}</Text>
      {section.editability ? (
        <></>
      ) : section.notificationCount ? (
        <Pressable
          style={styles.notificationContainer}
          onPress={() => navigation.navigate('Notifications' as never)}
        >
          <Text style={styles.notificationText}>
            {section.notificationCount}
          </Text>
        </Pressable>
      ) : (
        <View
          style={styles.notificationContainer2}
          //onPress={() => navigation.navigate('Notifications' as never)}
        >
          <Text style={styles.notificationText}>{'-'}</Text>
        </View>
      )}
    </View>
  ) : null;
};

const SettingsPage = () => {
  const { phoneNumber, selectedCode } = useLoginStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      selectedCode: state.selectedCode,
    };
  });
  const dbUserName = selectedCode.dial_code.replace('+', '') + phoneNumber;
  const instanceToken = axiosConfig();
  const navigation = useNavigation();
  const [image, setImage] = useState(null);
  const openModal = () => {
    setModalVisible(true);
  };
  const [notificationCount, setNotificationCount] = useState(0);
  const [hasGalleryPermission, setHasGalleryPermission] = useState(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [fontsLoaded] = Font.useFonts({
    'Inter-Regular': require('../../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });

  const pickImage = async () => {
    // No permissions request is necessary for launching the image library
    let result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      allowsEditing: true,
      aspect: [4, 3],
      //quality: 0.1,
      base64: true,
    });
    if (!result.canceled) {
      setImage(result.assets[0].base64);
      instanceToken
        .post(`/api/customers/${dbUserName}/profilePicture`, {
          image: result.assets[0].base64,
        })
        .then((res) => {
          console.log(res.data);
          Toast.show({
            type: 'success',
            text1: 'Profile picture updated',
            text2: 'Your profile picture has been updated successfully',
          });
        })
        .catch((err) => {
          console.log(err);
          Toast.show({
            type: 'error',
            text1: 'Error',
            text2: 'An error occured while updating your profile picture',
          });
        });
    }

    //console.log(result);
  };

  //  console.log(result);

  //  if (!result.canceled) {
  //    setImage(result.assets[0].uri);
  //  }
  //};

  const { isLogin, setIsLogin } = useLoginStore();
  const [coinBalance, setCoinBalance] = useState(200);
  const [section, setSection] = useState(editSections);
  const isFocused = useIsFocused();

  useEffect(() => {
    //console.log(isFocused);

    instanceToken
      .get(`/api/customers/${dbUserName}/friends/incoming_requests`)
      .then((res) => {
        //console.log(res.data, res.data.length);
        setNotificationCount(res.data.length);
      });

    instanceToken.get(`/api/customers/${dbUserName}/`).then((res) => {
      let section = [
        {
          name: 'First Name',
          value: res.data.name,
          editability: true,
          marginTop: 20,
        },
        {
          name: 'Last Name',
          value: res.data.surname,
          editability: true,
        },
        {
          name: 'Notfications',
          value: '',
          editability: false,
          borderBottomless: true,
          notificationCount: notificationCount,

          onPress: () => {},
        },
      ];
      setSection(section);
    });
    instanceToken
      .get(`/api/customers/${dbUserName}/profilePicture`)
      .then((res) => {
        setImage(res.data.image);
      });
    instanceToken
      .get(`api/wallet?username=${dbUserName}`)
      .then((res) => {
        setCoinBalance(res.data.balance);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [isFocused]);
  return fontsLoaded ? (
    <>
      <Modal
        animationType='fade'
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => {
          setModalVisible(!modalVisible);
        }}
      >
        <View style={styles.centeredView}>
          <View style={styles.modalView}>
            <Text style={styles.modalText}>Logging Out</Text>
            <View style={{ flex: 1, justifyContent: 'space-around' }}>
              <Text style={styles.modalTextStyle}>
                {'Are you sure you want to logout?'}
              </Text>
              <Pressable
                style={[styles.button, styles.buttonClose]}
                onPress={() => setIsLogin(false)}
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
      </Modal>
      <ScrollView>
        <View style={styles.container}>
          {image === null ? (
            <Image
              style={styles.profilePicture}
              source={avatarPlaceholder}
              resizeMode='contain'
            />
          ) : (
            <Image
              style={styles.profilePicture}
              source={{ uri: 'data:image/png;base64,' + image }}
              resizeMode='contain'
            />
          )}
          {/*<Image
            style={styles.profilePicture}
            source={{ uri: 'data:image/png;base64,' + image }}
            resizeMode='contain'
          />*/}
          <StyledButton
            buttonText='Change'
            onPress={async () => pickImage()}
            style={styles.changeButton}
          />
          {section.map((section) => (
            <EditProfileSection
              {...section}
              key={section.name}
              notificationCount={notificationCount}
            />
          ))}
          <View style={styles.bottomSection}>
            <Text style={styles.coinBalanceText}>Coin Balance</Text>
            <View style={styles.row_space_between_center}>
              <Text style={styles.coinAmountText}>{coinBalance}</Text>
              <Image source={CoinIcon} />
            </View>
          </View>

          <View style={styles.paymentMethodsContainer}>
            <View>
              <Text style={styles.mutedText}>Payment Methods</Text>
              <Text style={styles.whiteText}>Earn Coins</Text>
            </View>
            <Pressable
              style={styles.rightArrowContainer}
              onPress={() => navigation.navigate('CoinDetails')}
            >
              <Text style={styles.rightArrowText}>{'>'}</Text>
            </Pressable>
          </View>
          <StyledButton
            buttonText='Logout'
            style={styles.logoutButton}
            onPress={openModal}
          />
        </View>
      </ScrollView>
    </>
  ) : null;
};

const styles = StyleSheet.create({
  sectionName: { color: 'white', fontSize: 20 },
  editText: {
    color: Colors.purple.text,
    fontSize: 18,
    fontFamily: 'Inter-Regular',
  },
  notificationContainer: {
    width: 30,
    height: 30,
    backgroundColor: 'red',
    borderRadius: 15,
    marginTop: 0,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 0,
    flexDirection: 'row',
  },
  notificationContainer2: {
    width: 30,
    height: 30,
    //backgroundColor: 'red',
    borderRadius: 15,
    marginTop: 0,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 0,
    flexDirection: 'row',
  },
  notificationText: {
    fontFamily: 'Inter-Regular',
    fontSize: 20,
    color: 'white',
    marginHorizontal: 'auto',
  },
  editSectionContainer: {
    flexDirection: 'row',
    borderTopColor: '#202325',
    borderWidth: 1,
    justifyContent: 'space-between',
    width: '90%',
    paddingVertical: 15,
  },
  container: { flex: 1, alignItems: 'center' },
  profilePicture: {
    borderRadius: 40,
    width: 80,
    height: 80,
    marginTop: 25,
    backgroundColor: 'white',
  },
  logoutButton: {
    backgroundColor: Colors.red.error,
    marginBottom: 100,
  },
  changeButton: {
    backgroundColor: Colors.purple.lighter,
    marginTop: 40,
    text: { color: Colors.purple.settingsButton },
    width: '30%',
  },
  bottomSection: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: '90%',
    marginTop: 60,
  },
  coinBalanceText: {
    color: 'white',
    fontSize: 20,
    fontFamily: 'Inter-Regular',
  },
  row_space_between_center: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  coinAmountText: {
    color: 'white',
    fontSize: 20,
    fontFamily: 'Inter-Regular',
    marginRight: 5,
  },
  paymentMethodsContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: '90%',
    marginTop: 60,
  },
  mutedText: {
    color: Colors.gray.muted,
    fontSize: 15,
    fontFamily: 'Inter-Regular',
  },
  whiteText: {
    color: 'white',
    fontSize: 20,
    fontFamily: 'Inter-Regular',
  },
  rightArrowContainer: {
    alignItems: 'center',
    justifyContent: 'center',
    flexDirection: 'column',
    marginRight: 10,
  },
  rightArrowText: {
    color: 'white',
    fontSize: 20,
    fontFamily: 'Inter-Regular',
  },
  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    marginTop: 22,
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
  button: {
    borderRadius: 20,
    padding: 10,
    elevation: 2,
    alignItems: 'center',
  },

  buttonClose: {
    backgroundColor: Colors.red.error,
  },
});

export default SettingsPage;
