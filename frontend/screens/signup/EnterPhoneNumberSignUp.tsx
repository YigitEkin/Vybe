import React from 'react';
import EnterPhoneNumber from '../../components/2fa/EnterPhoneNumber';
import { useSignUpStore } from '../../stores/SignUpStore';
import { Keyboard, TouchableWithoutFeedback } from 'react-native';

const EnterPhoneNumberSignUp = ({ navigation }: any) => {
  const { phoneNumber, setPhoneNumber } = useSignUpStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      setPhoneNumber: state.setPhoneNumber,
    };
  });

  const DismissKeyboard = ({ children }) => (
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      {children}
    </TouchableWithoutFeedback>
  );

  return (
    //<DismissKeyboard>
    <EnterPhoneNumber
      buttonText='Sign Up'
      headerText='Welcome to Vybe'
      subHeaderText='Start Vybing'
      onPress={() => {
        phoneNumber &&
          phoneNumber.trim() !== '' &&
          navigation.navigate('SignupVerification');
      }}
      onChangeText={(text: string) => {
        setPhoneNumber(text);
      }}
      value={phoneNumber}
    />
    //</DismissKeyboard>
  );
};

export default EnterPhoneNumberSignUp;
