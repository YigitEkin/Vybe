import React, { useEffect, useState } from 'react';
import { StripeProvider, usePaymentSheet } from '@stripe/stripe-react-native';
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
  const { initPaymentSheet, presentPaymentSheet, loading } = usePaymentSheet();
  const [ready, setReady] = useState(false);

  useEffect(() => {
    initialisePaymentSheet();
  }, []);

  const initialisePaymentSheet = async () => {
    const;
  };

  async function buy() {
    const { error } = await presentPaymentSheet();

    if (error) {
      Alert.alert(`Error code: ${error.code}`, error.message);
    } else {
      Alert.alert('Success', 'The payment was confirmed successfully');
      setReady(false);
    }
  }
  return (
    <View style={{ flex: 1 }}>
      <StripeProvider publishableKey='pk_test_51N19G4Fn58HWM8DI9CdqhEL84LSRM5xjkKdKdxgxmrQLDFpLBGAw113LmN6tzbv2RdZimMJnb4ZcrWblQt9MvKdb00hto3rLCu'>
        <StyledButton buttonText={'buy'}>Buy</StyledButton>
      </StripeProvider>
    </View>
  );
};
export default PaymentByCard;
