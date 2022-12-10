import React, { useMemo, useRef } from "react";
import { TextInput, StyleSheet } from "react-native";
import Form from "../../components/Form/Form";

const SignUpMail = () => {
  const inputRef = useRef();
  const formItems = useMemo(
    () => [
      {
        label: "What's your email address?",
        component: (
          <TextInput
            selectTextOnFocus={true}
            keyboardAppearance="dark"
            keyboardType="email-address"
            style={styles.textInput}
          />
        ),
      },
    ],
    []
  );

  const navigateRoute = "SignUpUsername" as never;
  return (
    <Form
      items={formItems}
      currentStep={1}
      totalSteps={4}
      navigateRoute={navigateRoute}
    />
  );
};

const styles = StyleSheet.create({
  textInput: {
    width: "100%",
    height: 50,
    borderWidth: 2,
    borderColor: "#444",
    borderRadius: 5,
    color: "#fff",
    fontSize: 20,
    paddingLeft: 10,
    paddingRight: 10,
    marginTop: 20,
  },
});

export default SignUpMail;
