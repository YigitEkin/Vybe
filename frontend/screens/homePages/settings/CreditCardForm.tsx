import {
  CreditCardInput,
  LiteCreditCardInput,
} from 'react-native-credit-card-input-plus';
import React, { useEffect, useState } from 'react';
import { View, Text } from 'react-native';
import * as Font from 'expo-font';
import { useNavigation } from '@react-navigation/native';
import StyledButton from '../../../components/HomePage/StyledButton';
import { setGoogleApiKey } from 'expo-location';
import Toast from 'react-native-toast-message';

const CreditCardForm = ({ route }) => {
  const { amount, price } = route.params;
  const navigation = useNavigation();
  const [formData, setFormData] = useState({});
  const validateCard = (formData) => {
    if (formData.valid) {
      navigation.navigate('CoinDetails');

      Toast.show({
        type: 'success',
        text1: 'Succesful',
        text2: `You have purchased ${amount} coins 💰`,
      });
    } else {
      Toast.show({
        type: 'error',
        text1: 'Payment failed',
        text2: 'Please check your information',
      });
    }
  };
  const [fontsLoaded] = Font.useFonts({
    'Inter-Regular': require('../../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });
  return fontsLoaded ? (
    <View style={{ flex: 1 }}>
      <CreditCardInput
        requiresName={true}
        inputStyle={{ color: 'white' }}
        labelStyle={{ color: 'white' }}
        cardFontStyle='Inter'
        onChange={(data) => setFormData(data)}
      />
      <View style={{ marginVertical: 15, alignItems: 'center' }}>
        <Text style={{ color: 'white', fontSize: 20 }}>
          {`You will buy ${amount} coins for ${price}₺`}
        </Text>
      </View>
      <View style={{ alignItems: 'center' }}>
        <StyledButton buttonText='Buy' onPress={() => validateCard(formData)} />
      </View>
    </View>
  ) : null;
};
export default CreditCardForm;
