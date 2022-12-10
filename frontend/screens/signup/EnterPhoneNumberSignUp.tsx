import React from "react";
import EnterPhoneNumber from "../../components/2fa/EnterPhoneNumber";

const EnterPhoneNumberSignUp = ({ navigation }: any) => {
  return (
    <EnterPhoneNumber
      buttonText="Sign Up"
      headerText="Welcome to Vybe."
      subHeaderText="Start Vybing"
      onPress={() => navigation.navigate("SignUpMail")}
    />
  );
};

export default EnterPhoneNumberSignUp;
