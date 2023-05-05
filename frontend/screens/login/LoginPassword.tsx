import React, { useMemo, useState } from 'react';
import { TextInput, StyleSheet } from 'react-native';
import Form from '../../components/Form/Form';
import { useLoginStore } from '../../stores/LoginStore';
import axios from 'axios';
import { useNavigation } from '@react-navigation/native';
import { Toast } from 'react-native-toast-message/lib/src/Toast';

const LoginPassword = () => {
  const navigation = useNavigation();
  const { password, setPassword, phoneNumber, selectedCode } = useLoginStore(
    (state: any) => {
      return {
        selectedCode: state.selectedCode,
        phoneNumber: state.phoneNumber,
        password: state.password,
        setPassword: state.setPassword,
      };
    }
  );

  const formItems = useMemo(
    () => [
      {
        label: 'Enter your Password',
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
    ],
    []
  );
  const data = {
    username: selectedCode.dial_code.replace('+', '') + phoneNumber,
    password: password,
    code: '',
  };
  console.log(data);

  const navigateRoute = 'LoginVerification' as never;
  return (
    <Form
      cb={() =>
        axios
          .post('http://192.168.1.127:8080/api/auth/signIn', {
            username: selectedCode.dial_code.replace('+', '') + phoneNumber,
            password: password,
            code: '',
          })
          .then((res) => {
            if (res.data) {
              navigation.navigate('LoginVerification');
            } else {
              Toast.show({
                type: 'error',
                text1: 'Error validating',
                text2: 'Please check your credentials',
              });
            }
          })
          .catch((e) => console.log(e.message))
      }
      items={formItems}
      currentStep={1}
      totalSteps={1}
      navigateRoute={navigateRoute}
      validator={() => {
        return password && password.trim().length > 0;
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

export default LoginPassword;
