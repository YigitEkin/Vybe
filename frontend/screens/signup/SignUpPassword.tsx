import React, { useMemo, useState } from "react";
import { TextInput, StyleSheet } from "react-native";
import Form from "../../components/Form/Form";
import { useSignUpStore } from "../../stores/SignUpStore";

const SignUpPassword = () => {
  const { password, setPassword } = useSignUpStore((state: any) => {
    return {
      password: state.password,
      setPassword: state.setPassword,
    };
  });

  const [confirmPassword, setConfirmPassword] = useState<any>(null);

  const formItems = useMemo(
    () => [
      {
        label: "Enter a Password",
        component: (
          <TextInput
            secureTextEntry={true}
            selectTextOnFocus={true}
            keyboardAppearance="dark"
            value={password}
            onChangeText={(text) => {
              setPassword(text);
            }}
            style={styles.textInput}
          />
        ),
      },
      {
        label: "Confirm Password",
        component: (
          <TextInput
            secureTextEntry={true}
            value={confirmPassword}
            onChangeText={(text) => {
              setConfirmPassword(text);
            }}
            selectTextOnFocus={true}
            keyboardAppearance="dark"
            style={styles.textInput}
          />
        ),
      },
    ],
    []
  );

  const navigateRoute = "SignUpCompletedScreen" as never;
  return (
    <Form
      items={formItems}
      currentStep={3}
      totalSteps={4}
      navigateRoute={navigateRoute}
      validator={() => {
        return (
          password &&
          password.trim().length > 0 &&
          confirmPassword &&
          confirmPassword.trim().length > 0 &&
          password === confirmPassword
        );
      }}
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

export default SignUpPassword;
