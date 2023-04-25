import React, { useEffect, useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  KeyboardAvoidingView,
  TouchableHighlight,
} from 'react-native';
import * as Font from 'expo-font';
import StyledButton from '../../components/HomePage/StyledButton';
import OTPTextView from 'react-native-otp-textinput';
import { useLoginStore } from '../../stores/LoginStore';
import { DismissKeyboard } from '../../components/common/DismissKeyboard';

const LoginVerification = ({ navigation }: any) => {
  const [OTPCode, setOTPCode] = useState(0);
  const { phoneNumber, setPhoneNumber } = useLoginStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      setPhoneNumber: state.setPhoneNumber,
    };
  });
  const handleChange = (e) => {
    setOTPCode(e);
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
              Enter the 4-digit that we have sent via the phone number{' '}
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
            buttonText='Continue'
            onPress={() => navigation.navigate('HomeNotCheckedIn')}
          />
          <TouchableHighlight onPress={() => console.log('Pressed')}>
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
