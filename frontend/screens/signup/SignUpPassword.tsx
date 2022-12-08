import React, { useMemo } from "react";
import { TextInput, StyleSheet } from "react-native";
import Form from "../../components/Form/Form";

const SignUpPassword = () => {
  const formItems = useMemo(
    () => [
      {
        label: "Enter a Password",
        component: (
          <TextInput
            secureTextEntry={true}
            selectTextOnFocus={true}
            keyboardAppearance="dark"
            style={styles.textInput}
          />
        ),
      },
      {
        label: "Confirm Password",
        component: (
          <TextInput
            secureTextEntry={true}
            selectTextOnFocus={true}
            keyboardAppearance="dark"
            style={styles.textInput}
          />
        ),
      },
    ],
    []
  );

  return <Form items={formItems} currentStep={2} totalSteps={4} />;
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

export default SignUpPassword;
