import React, { useMemo } from 'react';
import { TextInput, StyleSheet } from 'react-native';
import Form from '../../components/Form/Form';
import { SignUpStore, useSignUpStore } from '../../stores/SignUpStore';

const SignUpUsername = () => {
  const {
    username,
    setUsername,
    firstName,
    setFirstName,
    lastName,
    setLastName,
  } = useSignUpStore((state: any) => {
    return {
      username: state.username,
      setUsername: state.setUsername,
      firstName: state.firstName,
      setFirstName: state.setFirstName,
      lastName: state.lastName,
      setLastName: state.setLastName,
    };
  });

  const formItems = useMemo(
    () => [
      {
        label: 'What’s your  name?',
        component: (
          <TextInput
            selectTextOnFocus={true}
            keyboardAppearance='dark'
            style={styles.textInput}
            placeholder='John Doe'
            placeholderTextColor={'#666'}
            onChangeText={(text) => {
              setUsername(text);
            }}
          />
        ),
      },
    ],
    []
  );

  const navigateRoute = 'SignUpPassword' as never;

  return (
    <Form
      items={formItems}
      currentStep={2}
      totalSteps={4}
      navigateRoute={navigateRoute}
      validator={() => {
        if (username.trim() === '') {
          return false;
        } else {
          setLastName(username.split(' ').slice(-1).join(' '));
          setFirstName(username.split(' ').slice(0, -1).join(' '));
          return true;
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
    fontFamily: 'Inter-Regular',
  },
});

export default SignUpUsername;
