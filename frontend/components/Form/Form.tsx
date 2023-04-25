import React from 'react';
import {
  Text,
  StyleSheet,
  Pressable,
  View,
  KeyboardAvoidingView,
} from 'react-native';
import StyledButton from '../HomePage/StyledButton';
import * as Font from 'expo-font';
import StatusBar from './StatusBar';
import { useNavigation } from '@react-navigation/native';
import { DismissKeyboard } from '../common/DismissKeyboard';

export type FormItem = {
  label: string;
  wrapperStyle?: any;
  labelStyle?: any;
  component: JSX.Element;
};

type FormProps = {
  items: FormItem[];
  currentStep: number;
  totalSteps: number;
  navigateRoute: never;
  validator: () => boolean;
  cb?: () => void;
};

type LabelProps = {
  label: string;
  wrapperStyle?: any;
  labelStyle?: any;
};

const Label = (item: LabelProps) => {
  const labelStyle = [styles.labelText];

  if (item.labelStyle) {
    labelStyle.push(item.labelStyle);
  }

  return item.wrapperStyle ? (
    <View style={item.wrapperStyle}>
      <Text style={labelStyle}>{item.label}</Text>
    </View>
  ) : (
    <Text style={labelStyle}>{item.label}</Text>
  );
};

const Form = ({
  items,
  currentStep,
  totalSteps,
  navigateRoute,
  validator,
  cb,
}: FormProps) => {
  const [fontsLoaded] = Font.useFonts({
    'Inter-Bold': require('../../assets/fonts/Inter/static/Inter-Bold.ttf'),
  });
  const navigation = useNavigation();
  const callback = cb
    ? cb
    : () => {
        navigation.navigate(navigateRoute);
      };

  return fontsLoaded ? (
    <>
      <StatusBar currentStep={currentStep} totalSteps={totalSteps} />
      <DismissKeyboard>
        <View style={styles.container}>
          <View style={styles.formAreaContainer}>
            {items.map((item, index) => (
              <View style={styles.inputContainer} key={index}>
                <Label
                  label={item.label}
                  wrapperStyle={item.wrapperStyle}
                  labelStyle={item.labelStyle}
                />
                {item.component}
              </View>
            ))}
          </View>
          <StyledButton
            style={styles.StyledButton}
            onPress={() => {
              validator() && callback();
            }}
            buttonText={'Continue'}
          />
        </View>
      </DismissKeyboard>
    </>
  ) : null;
};

const styles = StyleSheet.create({
  container: {
    justifyContent: 'flex-start',
    alignItems: 'center',
    flex: 1,
    width: '95%',
    marginTop: 30,
  },
  formAreaContainer: {
    width: '100%',
    justifyContent: 'flex-start',
    alignItems: 'flex-start',
  },
  inputContainer: {
    width: '100%',
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'flex-start',
    marginTop: 20,
  },
  labelText: {
    fontFamily: 'Inter-Bold',
    fontSize: 23,
    color: '#fff',
  },
  StyledButton: {
    marginTop: 'auto',
    marginBottom: 20,
  },
});

export default Form;
