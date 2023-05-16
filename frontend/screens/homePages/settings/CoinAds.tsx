import React, { useEffect, useState } from 'react';
import { Button, View } from 'react-native';
import {
  RewardedAd,
  RewardedAdEventType,
  TestIds,
} from 'react-native-google-mobile-ads';
import StyledButton from '../../../components/HomePage/StyledButton';

const adUnitId = __DEV__
  ? TestIds.REWARDED
  : 'ca-app-pub-9712054637149885/2757592836';

const rewarded = RewardedAd.createForAdRequest(adUnitId, {
  requestNonPersonalizedAdsOnly: true,
  keywords: ['gaming', 'sports'],
});

function CoinAds() {
  const [loaded, setLoaded] = useState(false);

  useEffect(() => {
    const unsubscribeLoaded = rewarded.addAdEventListener(
      RewardedAdEventType.LOADED,
      () => {
        setLoaded(true);
      }
    );
    const unsubscribeEarned = rewarded.addAdEventListener(
      RewardedAdEventType.EARNED_REWARD,
      (reward) => {
        console.log('User earned reward of ', reward);
      }
    );

    // Start loading the rewarded ad straight away
    rewarded.load();

    // Unsubscribe from events on unmount
    return () => {
      unsubscribeLoaded();
      unsubscribeEarned();
    };
  }, []);

  // No advert ready to show yet
  if (!loaded) {
    return null;
  }

  return (
    <View style={{ flex: 1, alignItems: 'center' }}>
      <StyledButton
        buttonText='Show Rewarded Ad'
        onPress={() => {
          rewarded.show();
        }}
      />
    </View>
  );
}
export default CoinAds;
