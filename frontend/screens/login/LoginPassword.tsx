import React, { useMemo, useState } from 'react';
import { TextInput, StyleSheet } from 'react-native';
import Form from '../../components/Form/Form';
import { useLoginStore } from '../../stores/LoginStore';
import axios from 'axios';
import { useNavigation } from '@react-navigation/native';
import { Toast } from 'react-native-toast-message/lib/src/Toast';
import axiosConfig from '../../constants/axiosConfig';

const LoginPassword = () => {
  const instanceToken = axiosConfig();
  const navigation = useNavigation();
  const [loading, setLoading] = useState(false);
  const { password, setPassword, phoneNumber, selectedCode, setToken, token } =
    useLoginStore((state: any) => {
      return {
        selectedCode: state.selectedCode,
        phoneNumber: state.phoneNumber,
        password: state.password,
        setPassword: state.setPassword,
        setToken: state.setToken,
        token: state.token,
      };
    });

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
      cb={() => {
        setLoading(true);
        instanceToken
          .post('/api/auth/signIn', {
            username: selectedCode.dial_code.replace('+', '') + phoneNumber,
            password: password,
            code: '',
          })
          .then((res) => {
            setLoading(false);
            if (res.data) {
              console.log(res.data);
              setToken(res.data);
              navigation.navigate('LoginVerification');
            } else {
              Toast.show({
                type: 'error',
                text1: 'Error validating',
                text2: 'Please check your credentials',
              });
            }
          })
          .catch((e) => {
            console.log(e.message);
            setLoading(false);
          });
      }}
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
