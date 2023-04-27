import React, { useEffect, useState } from 'react';
import InputSpinner from 'react-native-input-spinner';
import { Colors } from '../../../constants/Colors';

import {
  View,
  Text,
  StyleSheet,
  Image,
  Platform,
  Pressable,
  ScrollView,
  Alert,
} from 'react-native';
import StyledButton from '../../../components/HomePage/StyledButton';
import { useNavigation } from '@react-navigation/native';

const COIN_PRIZE = 0.05;

const PaymentByCard = () => {
  const navigation = useNavigation();
  const [amount, setAmount] = useState(50);
  return (
    <View style={{ flex: 1, alignItems: 'center' }}>
      {/*<StyledButton buttonText={'buy'}>Buy</StyledButton>
       */}
      <View style={{ marginBottom: 15 }}>
        <Text style={{ color: 'white', fontSize: 18 }}>
          {'How many coins would you like to purchase?'}
        </Text>
      </View>

      <View style={{ width: '65%', alignItems: 'center' }}>
        <InputSpinner
          max={10000}
          min={50}
          step={50}
          selectionColor='#ffffff'
          inputStyle={{ fontSize: 24 }}
          placeholder='5'
          color={Colors.purple.primary}
          background={Colors.purple.lighter}
          onChange={(val) => setAmount(val)}
        />
      </View>
      <View style={{ marginTop: 15 }}>
        <Text style={{ color: 'white', fontSize: 20 }}>
          Total price: {amount * COIN_PRIZE}₺
        </Text>
      </View>
      <View style={{ width: '70%', alignItems: 'center' }}>
        <StyledButton
          buttonText='Checkout'
          onPress={() => navigation.navigate('CreditCardForm')}
        ></StyledButton>
      </View>
    </View>
  );
};
export default PaymentByCard;
