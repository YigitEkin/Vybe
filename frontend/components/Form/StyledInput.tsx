import React from 'react';
import { View, StyleSheet, TextInput } from 'react-native';
import * as Font from 'expo-font';
import { Colors } from '../../constants/Colors';
import styled from 'styled-components/native';
interface StyledInputProps {
  count: number;
}
export const OTPInputSection = styled.View`
  justify-content: center;
  align-items: center;
  margin-vertical: 30px;
`;

const StyledInput = ({ count }: StyledInputProps) => {
  const [fontsLoaded] = Font.useFonts({
    'Inter-Bold': require('../../assets/fonts/Inter/static/Inter-Bold.ttf'),
    'Inter-Medium': require('../../assets/fonts/Inter/static/Inter-Medium.ttf'),
    'Inter-Regular': require('../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });
  return fontsLoaded ? (
    <View style={styles.container}>
      <TextInput style={styles.inputField} keyboardType='phone-pad' />
      <TextInput style={styles.inputField} keyboardType='phone-pad' />
      <TextInput style={styles.inputField} keyboardType='phone-pad' />
      <TextInput style={styles.inputField} keyboardType='phone-pad' />
    </View>
  ) : null;
};
const styles = StyleSheet.create({
  container: {
    width: '75%',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-around',
  },
  inputField: {
    height: 48,
    width: 48,
    borderColor: '#303437',
    borderWidth: 2,
    borderRadius: 64,
    textAlign: 'center',
    fontFamily: 'Inter-Bold',
    fontSize: 16,
    color: '#fff',
    //backgroundColor: 'white',
  },
});
export default StyledInput;
