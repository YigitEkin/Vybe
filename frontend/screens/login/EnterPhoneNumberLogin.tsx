import React, { useCallback } from 'react';
import { useLoginStore } from '../../stores/LoginStore';
import EnterPhoneNumber from '../../components/phoneCodePicker/PhoneCodePicker';

const EnterPhoneNumberLogin = ({ navigation }: any) => {
  const { phoneNumber, setPhoneNumber, selectedCode, setSelectedCode } =
    useLoginStore((state: any) => {
      return {
        phoneNumber: state.phoneNumber,
        setPhoneNumber: state.setPhoneNumber,
        selectedCode: state.selectedCode,
        setSelectedCode: state.setSelectedCode,
      };
    });

  const onPress = useCallback(() => {
    phoneNumber && phoneNumber.trim() !== ''
      ? navigation.navigate('LoginPassword')
      : null;
  }, [phoneNumber, setPhoneNumber, navigation]);

  return (
    <EnterPhoneNumber
      buttonText='Login'
      headerText='Welcome back.'
      subHeaderText='Log in to your account'
      mutedText='You will receive an SMS verification that may apply message and data rates.'
      onPress={onPress}
      setSelectedCallingCode={setSelectedCode}
      selectedCallingCode={selectedCode}
      phoneNumber={phoneNumber}
      setPhoneNumber={setPhoneNumber}
    />
  );
};

export default EnterPhoneNumberLogin;
