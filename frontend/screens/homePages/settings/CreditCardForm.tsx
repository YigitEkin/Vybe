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
import { useLoginStore } from '../../../stores/LoginStore';
import moment from 'moment';
import axiosConfig from '../../../constants/axiosConfig';

const CreditCardForm = ({ route }) => {
  const { phoneNumber, selectedCode } = useLoginStore((state: any) => {
    return {
      phoneNumber: state.phoneNumber,
      selectedCode: state.selectedCode,
    };
  });

  const dbUserName = selectedCode.dial_code.replace('+', '') + phoneNumber;

  const instanceToken = axiosConfig();
  const { amount, price } = route.params;
  const navigation = useNavigation();
  const [formData, setFormData] = useState({});
  const validateCard = (formData) => {
    console.log(formData);
    if (formData.valid) {
      //console.log(formData.values.number.split(' ').join(''));

      instanceToken
        .post(`/api/transactions/${dbUserName}`, {
          transactionType: 'CARD',
          receivedCoins: amount,
          date: moment(Date.now()).format('YYYY-MM-DD HH:mm:ss'),
          name: formData.values.name,
          surname: '',
          cardNumber: formData.values.number.split(' ').join(''),
          expirationMonth: formData.values.expiry.split('/')[0],
          expirationYear: formData.values.expiry.split('/')[1],
          cvc: formData.values.cvc,
        })
        .then((res) => {
          console.log(res);
          Toast.show({
            type: 'success',
            text1: 'Succesful',
            text2: `You have purchased ${amount} coins 💰`,
          });
          navigation.navigate('CoinDetails');
        })
        .catch((err) => {
          console.log(err.response.data);
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
