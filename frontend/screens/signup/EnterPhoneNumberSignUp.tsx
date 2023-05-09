import React, { useCallback } from 'react';
import EnterPhoneNumber from '../../components/phoneCodePicker/PhoneCodePicker';
import { useSignUpStore } from '../../stores/SignUpStore';
import { Keyboard, TouchableWithoutFeedback } from 'react-native';
import axios from 'axios';
import axiosConfig from '../../constants/axiosConfig';

const DismissKeyboard = ({ children }: any) => (
  <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
    {children}
  </TouchableWithoutFeedback>
);

const EnterPhoneNumberSignUp = ({ navigation }: any) => {
  const instanceToken = axiosConfig();
  const baseUrl = process.env.BASE_URL;
  const {
    phoneNumber,
    setPhoneNumber,
    selectedCode,
    setSelectedCode,
    firstName,
    setFirstName,
    lastName,
    setLastName,
  } = useSignUpStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      setPhoneNumber: state.setPhoneNumber,
      selectedCode: state.selectedCode,
      setSelectedCode: state.setSelectedCode,
      firstName: state.firstName,
      setFirstName: state.setFirstName,
      lastName: state.lastName,
      setLastName: state.setLastName,
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

    console.log(phoneNo);
    instanceToken
      .get(`/api/auth/2FA?phoneNumber=${phoneNo}`)
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
