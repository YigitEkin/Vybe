import React, { useCallback } from 'react';
import EnterPhoneNumber from '../../components/phoneCodePicker/PhoneCodePicker';
import { useSignUpStore } from '../../stores/SignUpStore';
import { Keyboard, TouchableWithoutFeedback } from 'react-native';
import axios from 'axios';

const DismissKeyboard = ({ children }: any) => (
  <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
    {children}
  </TouchableWithoutFeedback>
);

const EnterPhoneNumberSignUp = ({ navigation }: any) => {
  const { phoneNumber, setPhoneNumber, selectedCode, setSelectedCode } =
    useSignUpStore((state: any) => {
      return {
        phoneNumber: state.phoneNumber,
        setPhoneNumber: state.setPhoneNumber,
        selectedCode: state.selectedCode,
        setSelectedCode: state.setSelectedCode,
      };
    });

  const onPress = useCallback(() => {
    let phoneNo =
      phoneNumber && phoneNumber.trim() !== ''
        ? selectedCode.dial_code.replace('+', '') + phoneNumber
        : null;

    if (!phoneNo) {
      return;
    }

    console.log(phoneNo, phoneNumber);
    axios
      .get(`http://192.168.1.120:8080/api/auth/2FA?phoneNumber=${phoneNo}`)
      .then((res) => {
        if (res.data) {
          navigation.navigate('SignupVerification');
        }
      })
      .catch((e) => console.log(e.message));
  }, [phoneNumber, setPhoneNumber, navigation]);

  return (
    <DismissKeyboard>
      <EnterPhoneNumber
        buttonText='Sign Up'
        headerText='Welcome to Vybe'
        subHeaderText='Start Vybing'
        mutedText='You will receive an SMS verification that may apply message and data rates.'
        onPress={onPress}
        phoneNumber={phoneNumber}
        setPhoneNumber={setPhoneNumber}
        setSelectedCallingCode={setSelectedCode}
        selectedCallingCode={selectedCode}
      />
    </DismissKeyboard>
  );
};

export default EnterPhoneNumberSignUp;
