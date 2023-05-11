import React, { useMemo, useState } from 'react';
import { TextInput, StyleSheet } from 'react-native';
import Form from '../../components/Form/Form';
import { useSignUpStore } from '../../stores/SignUpStore';
import { Toast } from 'react-native-toast-message/lib/src/Toast';

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
        label: 'Enter a Password',
        component: (
          <TextInput
            secureTextEntry={true}
            selectTextOnFocus={true}
            keyboardAppearance='dark'
            placeholder='Password'
            placeholderTextColor={'#666'}
            onChangeText={(text) => {
              setPassword(text);
            }}
            style={styles.textInput}
          />
        ),
      },
      {
        label: 'Confirm Password',
        component: (
          <TextInput
            secureTextEntry={true}
            value={confirmPassword}
            onChangeText={(text) => {
              setConfirmPassword(text);
            }}
            placeholder='Confirm Password'
            placeholderTextColor={'#666'}
            selectTextOnFocus={true}
            keyboardAppearance='dark'
            style={styles.textInput}
          />
        ),
      },
    ],
    []
  );

  const navigateRoute = 'EnterPhoneNumberSignUp' as never;
  return (
    <Form
      items={formItems}
      currentStep={3}
      totalSteps={4}
      navigateRoute={navigateRoute}
      validator={() => {
        if (
          password &&
          password.trim().length >= 8 &&
          confirmPassword &&
          confirmPassword.trim().length >= 8 &&
          password === confirmPassword
        ) {
          return true;
        } else if (password !== confirmPassword) {
          Toast.show({
            type: 'error',
            text1: 'Error',
            text2: 'Your passwords do not match',
          });
        } else {
          Toast.show({
            type: 'error',
            text1: 'Error',
            text2: 'Your password must be at least 8 characters long',
          });
        }
        return false;
      }}
    />
  );
};

const styles = StyleSheet.create({
  textInput: {
    width: '100%',
    height: 50,
    borderWidth: 2,
    borderColor: '#444',
    borderRadius: 5,
    color: '#fff',
    fontSize: 20,
    paddingLeft: 10,
    paddingRight: 10,
    marginTop: 20,
  },
});

export default SignUpPassword;
