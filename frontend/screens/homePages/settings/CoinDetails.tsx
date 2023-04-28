import React, { useEffect } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image,
  Platform,
  Pressable,
  ScrollView,
  ActivityIndicator,
} from 'react-native';
import * as Font from 'expo-font';
import watchAdsIcon from '../../../assets/watchAdsButton.png';
import creditCardIcon from '../../../assets/CCard.png';
import { useNavigation } from '@react-navigation/native';
import Toast from 'react-native-toast-message';
import {
  RewardedAd,
  RewardedAdEventType,
  AdEventType,
  TestIds,
} from 'react-native-google-mobile-ads';

const adUnitId = __DEV__
  ? TestIds.REWARDED
  : 'ca-app-pub-9712054637149885/2757592836';

const data = [
  {
    method: 'Credit Card',
    date: '12 Apr 2023',
    price: 500.0,
  },
  {
    method: 'Credit Card',
    date: '12 Apr 2023',
    price: 500.0,
  },
  {
    method: 'Credit Card',
    date: '12 Apr 2023',
    price: 500.0,
  },
  {
    method: 'Credit Card',
    date: '12 Apr 2023',
    price: 500.0,
  },
  {
    method: 'Credit Card',
    date: '12 Apr 2023',
    price: 500.0,
  },
];
const rewarded = RewardedAd.createForAdRequest(adUnitId);

const CoinDetails = () => {
  const navigation = useNavigation();
  const [loaded, setLoaded] = React.useState(false);
  const [amount, setAmount] = React.useState(0);
  const [fontsLoaded] = Font.useFonts({
    'Inter-Medium': require('../../../assets/fonts/Inter/static/Inter-Medium.ttf'),
    'Inter-Bold': require('../../../assets/fonts/Inter/static/Inter-Bold.ttf'),
  });

  useEffect(() => {
    rewarded.load();
    //console.log(rewarded);
    const unsubscribeLoaded = rewarded.addAdEventListener(
      RewardedAdEventType.LOADED,
      () => {
        setLoaded(true);
        console.log('loaded');
      }
    );
    const unsubscribeEarned = rewarded.addAdEventListener(
      RewardedAdEventType.EARNED_REWARD,
      (reward) => {
        console.log('User earned reward of ', reward.amount);
        setAmount(reward.amount);
      }
    );
    const unsubscribeClosed = rewarded.addAdEventListener(
      AdEventType.CLOSED,
      (reward) => {
        console.log('Ad closed');
        setLoaded(false);
        rewarded.load();
        Toast.show({
          type: 'success',
          text1: 'Congratulations',
          text2: `You earned 10 coins 💰`,
        });
      }
    );

    // Start loading the rewarded ad straight away
    //rewarded.load();

    // Unsubscribe from events on unmount
    return () => {
      unsubscribeLoaded();
      unsubscribeEarned();
      unsubscribeClosed();
    };
  }, []);
  if (!loaded) {
    return (
      <View style={styles.container}>
        <Text style={styles.headerText}>{'Earn More Coins'}</Text>
        <View style={styles.buttonContainer}>
          <View style={styles.buttonView}>
            <Text style={styles.textStyle}>{'Watch Ads'}</Text>
            <Pressable
              style={styles.button}
              disabled={!loaded}
              onPress={() => {
                rewarded.show();
                console.log('load');
              }}
            >
              <ActivityIndicator />
            </Pressable>

            {/*<Pressable
            style={styles.button}
            onPress={() => navigation.navigate('CoinAds')}
          >
            {!loaded ? (
              <ActivityIndicator />
            ) : (
              <Image source={watchAdsIcon}></Image>
            )}
          </Pressable>*/}
          </View>
          <View style={styles.buttonView}>
            <Text style={styles.textStyle}>{'Buy Coins'}</Text>
            <Pressable
              style={styles.button}
              onPress={() => navigation.navigate('PayByCard')}
            >
              <Image source={creditCardIcon}></Image>
            </Pressable>
          </View>
        </View>
        <Text style={styles.headerText}>{'Payments'}</Text>
        <ScrollView style={styles.scrollView}>
          {data.map((item, index) => {
            return (
              <View style={styles.scrollItem} key={index}>
                <View style={styles.mainTextContainer}>
                  <View style={styles.infoTextContainer}>
                    <Text style={styles.textMethod}>{item.method}</Text>
                    <Text style={styles.textDate}>{item.date}</Text>
                  </View>
                  <View style={styles.priceInfoContainer}>
                    <Text style={styles.textPrice}>$ {item.price}</Text>
                  </View>
                </View>
              </View>
            );
          })}
        </ScrollView>
      </View>
    );
  }

  return fontsLoaded ? (
    <View style={styles.container}>
      <Text style={styles.headerText}>{'Earn More Coins'}</Text>
      <View style={styles.buttonContainer}>
        <View style={styles.buttonView}>
          <Text style={styles.textStyle}>{'Watch Ads'}</Text>
          <Pressable
            style={styles.button}
            disabled={!loaded}
            onPress={() => {
              rewarded.show();
              console.log('load');
            }}
          >
            <Image source={watchAdsIcon}></Image>
          </Pressable>

          {/*<Pressable
            style={styles.button}
            onPress={() => navigation.navigate('CoinAds')}
          >
            {!loaded ? (
              <ActivityIndicator />
            ) : (
              <Image source={watchAdsIcon}></Image>
            )}
          </Pressable>*/}
        </View>
        <View style={styles.buttonView}>
          <Text style={styles.textStyle}>{'Buy Coins'}</Text>
          <Pressable
            style={styles.button}
            onPress={() => navigation.navigate('PayByCard')}
          >
            <Image source={creditCardIcon}></Image>
          </Pressable>
        </View>
      </View>
      <Text style={styles.headerText}>{'Payments'}</Text>
      <ScrollView style={styles.scrollView}>
        {data.map((item, index) => {
          return (
            <View style={styles.scrollItem} key={index}>
              <View style={styles.mainTextContainer}>
                <View style={styles.infoTextContainer}>
                  <Text style={styles.textMethod}>{item.method}</Text>
                  <Text style={styles.textDate}>{item.date}</Text>
                </View>
                <View style={styles.priceInfoContainer}>
                  <Text style={styles.textPrice}>$ {item.price}</Text>
                </View>
              </View>
            </View>
          );
        })}
      </ScrollView>
    </View>
  ) : null;
};

const styles = StyleSheet.create({
  container: { flex: 1, alignItems: 'center' },
  headerText: { fontFamily: 'Inter-Bold', color: 'white', fontSize: 20 },
  textStyle: { fontFamily: 'Inter-Medium', color: 'white' },
  buttonView: {
    justifyContent: 'space-between',
    alignItems: 'center',
    flexDirection: 'row',
    width: '100%',
    marginVertical: 15,
  },
  buttonContainer: {
    width: '85%',
    marginTop: 60,
    marginBottom: 20,
  },
  button: {
    width: 70,
    height: 35,
    backgroundColor: 'white',
    borderRadius: 15,
    alignItems: 'center',
    justifyContent: 'center',
  },
  scrollView: {
    width: '95%',
    //marginTop: 50,
    marginBottom: 80,
  },
  scrollItem: {
    width: '100%',
    backgroundColor: 'white',
    height: 85,
    borderRadius: 15,
    marginVertical: 10,
  },
  mainTextContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    height: '100%',
    padding: 15,
  },
  infoTextContainer: {
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'space-between',
    height: '100%',
  },
  priceInfoContainer: {
    justifyContent: 'center',
  },
  textMethod: {
    fontFamily: 'Inter-Medium',
    fontSize: 18,
  },
  textDate: {
    fontFamily: 'Inter-Medium',
    fontSize: 18,
    color: '#808F9B',
  },
  textPrice: {
    fontFamily: 'Inter-Medium',
    fontSize: 18,
    color: '#F81506',
  },
});
export default CoinDetails;
