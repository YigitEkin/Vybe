import React, { useMemo } from "react";
import { TextInput, StyleSheet } from "react-native";
import Form from "../../components/Form/Form";

const SignUpUsername = () => {
  const formItems = useMemo(
    () => [
      {
        label: "What’s your  name?",
        component: (
          <TextInput
            selectTextOnFocus={true}
            keyboardAppearance="dark"
            style={styles.textInput}
          />
        ),
      },
    ],
    []
  );

  const navigateRoute = "SignUpPassword" as never;

  return (
    <Form
      items={formItems}
      currentStep={2}
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
    fontFamily: "Inter-Regular",
  },
});

export default SignUpUsername;
