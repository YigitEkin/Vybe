import { StyleSheet, Text, View, ImageBackground } from 'react-native';
import React, { useEffect, useState } from 'react';
import * as Font from 'expo-font';
type FriendItemProps = {
  name?: String;
  venue?: String;
  profilePic?: any;
};
const FriendItem = ({ name, venue, profilePic }: any) => {
  const [fontsLoaded] = Font.useFonts({
    'Inter-Bold': require('../../assets/fonts/Inter/static/Inter-Bold.ttf'),
    'Inter-Medium': require('../../assets/fonts/Inter/static/Inter-Medium.ttf'),
    'Inter-Regular': require('../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });
  return (
    <View style={styles.container}>
      <Text style={{ color: '#fff' }}>{'ASD'}</Text>
    </View>
  );
};
const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: '100%',
    backgroundColor: '#fff',
    height: 10,
  },
});

export default FriendItem;
