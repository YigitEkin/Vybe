import React from "react";
import EnterPhoneNumber from "../../components/2fa/EnterPhoneNumber";
import { useLoginStore } from "../../stores/LoginStore";

const EnterPhoneNumberLogin = () => {
  const { phoneNumber, setPhoneNumber } = useLoginStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      setPhoneNumber: state.setPhoneNumber,
    };
  });

  return (
    <EnterPhoneNumber
      buttonText="Login"
      headerText="Welcome Back."
      subHeaderText="Log in to your account"
      onChangeText={(text: string) => {
        setPhoneNumber(text);
      }}
      value={phoneNumber}
      onPress={() => {
        //TODO: when implemented, navigate to the next screen
        // phoneNumber && navigation.navigate("EnterVerificationCode");
      }}
    />
  );
};

export default EnterPhoneNumberLogin;
