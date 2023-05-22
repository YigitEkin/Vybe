import React, { useEffect, useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  KeyboardAvoidingView,
  TouchableHighlight,
  ActivityIndicator,
} from 'react-native';
import * as Font from 'expo-font';
import StyledButton from '../../components/HomePage/StyledButton';
import OTPTextView from 'react-native-otp-textinput';
import { useLoginStore } from '../../stores/LoginStore';
import { DismissKeyboard } from '../../components/common/DismissKeyboard';
import { deFormatPhoneNumber } from '../../components/phoneCodePicker/utils/helpers';
import axios from 'axios';
import { Toast } from 'react-native-toast-message/lib/src/Toast';
import axiosConfig from '../../constants/axiosConfig';
import { useCheckedInStore } from '../../stores/CheckedInStore';

const LoginVerification = ({ navigation }: any) => {
  const instanceToken = axiosConfig();
  const [OTPCode, setOTPCode] = useState(0);
  const [loading, setLoading] = useState(false);
  const { setIsCheckIn } = useCheckedInStore((state: any) => ({
    setIsCheckIn: state.setIsCheckIn,
  }));
  const {
    phoneNumber,
    password,
    setIsLogin,
    selectedCode,
    setPhoneNumber,
    setWalletId,
  } = useLoginStore((state: any) => {
    return {
      password: state.password,
      phoneNumber: state.phoneNumber,
      setIsLogin: state.setIsLogin,
      selectedCode: state.selectedCode,
      setPhoneNumber: state.setPhoneNumber,
      setWalletId: state.setWalletId,
    };
  });
  const handleChange = (e) => {
    setOTPCode(e);
  };
  const resendCode = () => {
    instanceToken
      .post('/api/auth/signIn', {
        username: selectedCode.dial_code.replace('+', '') + phoneNumber,
        password: password,
        code: '',
      })
      .then((res) => {
        setLoading(false);
        if (res.data) {
          console.log(res.data);
          setToken(res.data);
          navigation.navigate('LoginVerification');
        } else {
          Toast.show({
            type: 'error',
            text1: 'Error validating',
            text2: 'Please check your credentials',
          });
        }
      })
      .catch((e) => {
        console.log(e.message);
        setLoading(false);
      });
  };
  const data = {
    username: selectedCode.dial_code.replace('+', '') + phoneNumber,
    password: password,
    code: String(OTPCode),
  };
  const handleSubmit = () => {
    setLoading(true);
    String(OTPCode).length === 4 &&
      instanceToken
        .post('/api/auth/customer/2FA', data)
        .then((res) => {
          setLoading(false);
          if (res.data) {
            setWalletId(res.data.walletID);
            //setPhoneNumber(null);
            setIsLogin(true);
            setIsCheckIn(res.data.checkedInVenue !== null);
          } else {
            Toast.show({
              type: 'error',
              text1: 'Error',
              text2: 'Your code is not correct!',
            });
          }
        })
        .catch((e) => {
          console.log(e);
          setLoading(false);
        });
  };
  const [fontsLoaded] = Font.useFonts({
    'Inter-Bold': require('../../assets/fonts/Inter/static/Inter-Bold.ttf'),
    'Inter-Medium': require('../../assets/fonts/Inter/static/Inter-Medium.ttf'),
    'Inter-Regular': require('../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });
  return fontsLoaded ? (
    <DismissKeyboard>
      <View style={styles.container}>
        <View style={styles.formAreaContainer}>
          <View style={styles.inputContainer}>
            <Text style={styles.headerText}>
              {'Enter authentication code.'}
            </Text>
            <Text style={styles.subHeaderText}>
              Enter the 4-digit that we have sent via the phone number
              {' ' + `${selectedCode.dial_code}`}
              {phoneNumber}
            </Text>
          </View>
          <View style={styles.inputContainer}>
            <OTPTextView
              //style={styles.roundedTextInput}
              offTintColor={'#303437'}
              tintColor={'#6B4EFF'}
              //containerStyle={styles.roundedTextInput}
              textInputStyle={[styles.textStyle, styles.roundedTextInput]}
              handleTextChange={(e) => handleChange(e)}
            ></OTPTextView>
          </View>
        </View>
        <View style={styles.StyledButton}>
          <StyledButton
            style={styles.StyledButton}
            buttonText={
              loading ? <ActivityIndicator color='#EA34C9' /> : 'Continue'
            }
            onPress={handleSubmit}
          />
          <TouchableHighlight onPress={() => resendCode()}>
            <Text style={styles.resendText}>{'Resend Code'}</Text>
          </TouchableHighlight>
        </View>
      </View>
    </DismissKeyboard>
  ) : null;
};
const styles = StyleSheet.create({
  headerText: {
    fontFamily: 'Inter-Bold',
    fontSize: 24,
    color: '#fff',
    textAlign: 'center',
  },
  subHeaderText: {
    marginTop: 5,
    fontFamily: 'Inter-Regular',
    fontSize: 16,
    color: '#979C9E',
    textAlign: 'center',
  },
  StyledButton: {
    marginTop: 'auto',
    marginBottom: 20,
    width: '100%',
    alignItems: 'center',
  },
  container: {
    flex: 1,
    backgroundColor: '#000',
    //justifyContent: 'center',
    alignItems: 'center',
    width: '95%',
  },
  formAreaContainer: {
    width: '100%',
    justifyContent: 'flex-start',
    alignItems: 'center',
    marginTop: '30%',
  },
  inputContainer: {
    width: '100%',
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'center',
    marginTop: 20,
  },
  roundedTextInput: {
    borderRadius: 100,
    borderWidth: 2,
    borderBottomWidth: 2,
  },
  textStyle: {
    color: '#FFFFFF',
  },
  resendText: {
    color: '#9990FF',
    fontFamily: 'Inter-Regular',
    fontSize: 16,
  },
});
export default LoginVerification;
