import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import SettingsPage from './Settings';
import Notifications from './Notifications';
import { NavigationContainer } from '@react-navigation/native';
import CoinDetails from './CoinDetails';
import PaymentByCard from './PaymentByCard';
import CreditCardForm from './CreditCardForm';
import CoinAds from './CoinAds';

const screenOptions = {
  contentStyle: {
    backgroundColor: '#000',
    width: '100%',
  },
  headerStyle: {
    backgroundColor: '#000',
    shadowColor: 'transparent',
    height: 10,
  },
  headerTintColor: '#fff',
  headerTitle: '',
};

const SettingsNavigationWrapper = () => {
  const Stack = createNativeStackNavigator();

  return (
    <Stack.Navigator screenOptions={screenOptions} initialRouteName='Profile'>
      <Stack.Screen
        options={{
          headerShown: false,
          headerBackTitleVisible: false,
        }}
        name='Profile'
        component={SettingsPage}
      />
      <Stack.Screen name='Notifications' component={Notifications} />
      <Stack.Screen name='CoinDetails' component={CoinDetails} />
      <Stack.Screen name='PayByCard' component={PaymentByCard} />
      <Stack.Screen name='CreditCardForm' component={CreditCardForm} />
      <Stack.Screen name='CoinAds' component={CoinAds} />
    </Stack.Navigator>
  );
};

export default SettingsNavigationWrapper;
