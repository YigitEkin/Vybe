import {
  CreditCardInput,
  LiteCreditCardInput,
} from 'react-native-credit-card-input-plus';
import React, { useEffect, useState } from 'react';
import { View } from 'react-native';
import * as Font from 'expo-font';

const CreditCardForm = () => {
  const [fontsLoaded] = Font.useFonts({
    'Inter-Regular': require('../../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });
  return (
    <View style={{ flex: 1 }}>
      <CreditCardInput
        requiesName={true}
        inputStyle={{ color: 'white' }}
        labelStyle={{ color: 'white' }}
        cardFontStyle='Inter'
      />
    </View>
  );
};
export default CreditCardForm;
