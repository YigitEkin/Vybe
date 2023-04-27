import React, { useEffect, useState } from 'react';

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

const PaymentByCard = () => {
  return (
    <View style={{ flex: 1 }}>
      <StyledButton buttonText={'buy'}>Buy</StyledButton>
    </View>
  );
};
export default PaymentByCard;
