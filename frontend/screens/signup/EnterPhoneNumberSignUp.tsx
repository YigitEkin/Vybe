import React from 'react';
import EnterPhoneNumber from '../../components/2fa/EnterPhoneNumber';
import { useSignUpStore } from '../../stores/SignUpStore';

const EnterPhoneNumberSignUp = ({ navigation }: any) => {
  const { phoneNumber, setPhoneNumber } = useSignUpStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      setPhoneNumber: state.setPhoneNumber,
    };
  });

  return (
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
  );
};

export default EnterPhoneNumberSignUp;
