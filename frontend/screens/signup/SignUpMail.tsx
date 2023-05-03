import React, { useMemo, useRef } from 'react';
import { TextInput, StyleSheet } from 'react-native';
import Form from '../../components/Form/Form';
import { useSignUpStore } from '../../stores/SignUpStore';
import { DismissKeyboard } from '../../components/common/DismissKeyboard';
import { Toast } from 'react-native-toast-message/lib/src/Toast';

//write a function that checks if the email is valid
const validateEmail = (email: string) => {
  const re = /\S+@\S+\.\S+/;
  return re.test(email);
};

const SignUpMail = () => {
  const { email, setEmail } = useSignUpStore((state: any) => {
    return {
      email: state.email,
      setEmail: state.setEmail,
    };
  });
  const formItems = useMemo(
    () => [
      {
        label: "What's your email address?",
        component: (
          <TextInput
            selectTextOnFocus={true}
            placeholder='a@b.com'
            autoCapitalize='none'
            placeholderTextColor={'#666'}
            keyboardAppearance='dark'
            keyboardType='email-address'
            style={styles.textInput}
            onChangeText={(text) => {
              setEmail(text);
            }}
          />
        ),
      },
    ],
    []
  );

  const navigateRoute = 'SignUpUsername' as never;
  return (
    <Form
      items={formItems}
      currentStep={1}
      totalSteps={4}
      navigateRoute={navigateRoute}
      validator={() => {
        if (email && email.trim().length > 0 && validateEmail(email)) {
          return true;
        } else {
          Toast.show({
            type: 'error',
            text1: 'Error',
            text2: 'Please provide a valid email',
          });
          return false;
        }
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

export default SignUpMail;
