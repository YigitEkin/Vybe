import { StyleSheet, Text, View, ImageBackground, Image } from 'react-native';
import React, { useEffect, useState } from 'react';
import * as Font from 'expo-font';
import Icon from '../../assets/appIcon.png';
type ListItemProps = {
  name?: String;
  venue?: String;
  profilePic?: any;
};
const ListItem = ({ topText, subText, profilePic }: any) => {
  const [fontsLoaded] = Font.useFonts({
    'Inter-Bold': require('../../assets/fonts/Inter/static/Inter-Bold.ttf'),
    'Inter-Medium': require('../../assets/fonts/Inter/static/Inter-Medium.ttf'),
    'Inter-Regular': require('../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });
  return (
    <View style={styles.container}>
      <View
        style={{
          flex: 1,
          flexDirection: 'row',
          alignItems: 'center',
        }}
      >
        <Image
          style={styles.profilePicture}
          source={
            profilePic
              ? { uri: profilePic }
              : require('../../assets/avatarPlaceholder.png')
          }
          resizeMode='cover'
        />
        <View
          style={{
            marginLeft: 20,
            height: '100%',
            justifyContent: 'space-around',
          }}
        >
          <Text
            style={{
              color: '#fff',
              fontSize: 18,
              fontFamily: 'Inter-Medium',
            }}
          >
            {topText}
          </Text>
          <Text
            style={{
              color: '#979c9e',
              fontSize: 16,
              fontFamily: 'Inter-Medium',
            }}
          >
            {subText}
          </Text>
        </View>
      </View>
    </View>
  );
};
const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    width: '100%',
    backgroundColor: '#000',
    height: 60,
    marginBottom: 10,
    marginTop: 10,
  },
  profilePicture: {
    borderRadius: 40,
    width: 55,
    height: 55,
    backgroundColor: '#fff',
  },
});

export default ListItem;
