import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  Image,
  Pressable,
  ScrollView,
} from 'react-native';
import { MapItem } from './Map';
import { NotificationCardProps } from '../settings/Notifications';
import { Colors } from '../../../constants/Colors';
import { Entypo } from '@expo/vector-icons';
import * as Font from 'expo-font';
import moment from 'moment';
import { FontAwesome } from '@expo/vector-icons';
import StyledButton from '../../../components/HomePage/StyledButton';
import { useNavigation, useRoute } from '@react-navigation/native';

interface Comment extends NotificationCardProps {}
function NotificationCard(data: NotificationCardProps) {
  const [fontsLoaded] = Font.useFonts({
    'Inter-Bold': require('../../../assets/fonts/Inter/static/Inter-Bold.ttf'),
    'Inter-Regular': require('../../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });

  return fontsLoaded ? (
    <View style={styles.container}>
      <View style={styles.basis80}>
        <View style={styles.row_start_center}>
          <View style={styles.picture} />
          <View style={styles.ml_20}>
            <Text style={styles.username}>{data.user}</Text>
            <Text style={styles.desc}>{data.description}</Text>
          </View>
        </View>
        <View style={styles.time}>
          <Text style={styles.timeText}>
            {Math.abs(moment(data.time).diff(moment(), 'minutes')) +
              ' minutes ago'}
          </Text>
        </View>
      </View>
    </View>
  ) : null;
}

interface VenueDetails {
  id: number;
  title: string;
  location: string;
  currentlyPlaying: string;
  image: any;
  rating: number;
  reviews: number;
  comments: Comment[];
}

const data: VenueDetails = {
  id: 1,
  title: 'Federal Coffee',
  location: 'Bilkent, Ankara',
  currentlyPlaying: 'Gunes - Suclarimdan Biri',
  image: require('../../../assets/fedo2.png'),
  rating: 4.65,
  reviews: 100,
  comments: [
    {
      id: 1,
      user: 'Mehmet Berk Türkçapar',
      description: 'Awesome place!',
      time: new Date(),
    },
    {
      id: 2,
      user: 'Mehmet Berk Türkçapar',
      description: 'Awesome place!',
      time: new Date(),
    },
    {
      id: 3,
      user: 'Mehmet Berk Türkçapar',
      description: 'Music are too loud!',
      time: new Date(),
    },
    {
      id: 4,
      user: 'Mehmet Berk Türkçapar',
      description: 'Music are too loud!',
      time: new Date(),
    },
  ],
};

const VenueDetails = () => {
  const [fontsLoaded] = Font.useFonts({
    'Inter-Bold': require('../../../assets/fonts/Inter/static/Inter-Bold.ttf'),
    'Inter-Regular': require('../../../assets/fonts/Inter/static/Inter-Regular.ttf'),
  });

  const navigation = useNavigation();
  const route = useRoute();
  // @ts-ignore
  const { id } = route.params;

  return fontsLoaded ? (
    <View style={styles.Topcontainer}>
      <View style={styles.content}>
        <Image
          source={require('../../../assets/fedo2.png')}
          style={styles.img}
          resizeMode='cover'
        />
      </View>
      <View style={styles.topContentContainer}>
        <View style={styles.topContentInnerContainer}>
          <Image
            source={require('../../../assets/fedo2.png')}
            style={styles.venueImg}
            resizeMode='cover'
          />
        </View>
        <View style={styles.ph20}>
          <View style={styles.venueInfo}>
            <Text style={styles.venueName}>{data.title}</Text>
            <View style={{ flexDirection: 'row' }}>
              <Entypo name='location-pin' size={16} color={Colors.gray.muted} />
              <Text style={[styles.smallText, { marginLeft: 5 }]}>
                {data.location}
              </Text>
            </View>
            <Text style={styles.smallText}>
              {`Currently Playing:\n${data.currentlyPlaying}`}
            </Text>
          </View>
        </View>
      </View>
      <View style={styles.lowerContentContainer}>
        <View style={styles.lowerContentInnerContainer}>
          <View style={styles.boldTextContainer}>
            <Text style={styles.boldText}>Vybe of This Venue</Text>
            <View style={styles.row}>
              <FontAwesome
                style={styles.ml_auto}
                name={'star'}
                size={16}
                color={'#f1c40f'}
              />
              <Text style={styles.boldText}>{data.rating}</Text>
            </View>
          </View>
        </View>
        <ScrollView style={styles.mh_240}>
          {data.comments.map((comment) => (
            <NotificationCard
              id={comment.id}
              key={comment.id}
              user={comment.user}
              description={comment.description}
              time={comment.time}
            />
          ))}
        </ScrollView>
        <View style={styles.alignCenter}>
          <StyledButton
            buttonText='Make a Review'
            onPress={(e) => {
              navigation.navigate('AddVenueReview', { id: id });
            }}
          />
        </View>
      </View>
    </View>
  ) : null;
};

const styles = StyleSheet.create({
  venueInfo: {
    height: 150,
    flexDirection: 'column',
    justifyContent: 'space-around',
  },
  venueName: { color: 'white', fontSize: 30, fontFamily: 'Inter-Bold' },
  lowerContentContainer: {
    flex: 2,
    backgroundColor: '#011725',
    borderTopColor: Colors.gray.muted,
    borderTopWidth: 1,
    paddingHorizontal: 10,
    paddingTop: 10,
  },
  lowerContentInnerContainer: {
    paddingHorizontal: 10,
    flexDirection: 'row',
    justifyContent: 'center',
  },
  smallText: {
    color: Colors.gray.muted,
    fontSize: 12,
    fontFamily: 'Inter-Regular',
  },
  boldTextContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginVertical: 20,
    flex: 1,
  },
  ml_auto: { marginLeft: 'auto' },
  row: { flexDirection: 'row' },
  boldText: { color: '#fff', fontSize: 16, fontFamily: 'Inter-Bold' },
  mh_240: { maxHeight: 240 },
  alignCenter: { alignItems: 'center' },
  ph20: { paddingHorizontal: 20 },
  venueImg: {
    width: '100%',
    height: '100%',
    borderRadius: 0.5,
    zIndex: 2,
    position: 'absolute',
    top: -50,
    left: 10,
  },
  topContentInnerContainer: {
    flexDirection: 'column',
    alignItems: 'center',
    flex: 1,
    position: 'relative',
  },
  topContentContainer: {
    backgroundColor: '#011725',
    flexDirection: 'row',
    paddingHorizontal: 10,
    paddingBottom: 10,
  },
  content: { flex: 1, backgroundColor: 'black' },
  img: {
    width: '80%',
    height: '100%',
  },
  container: {
    backgroundColor: 'white',
    padding: 20,
    borderRadius: 10,
    margin: 10,
    shadowColor: '#000',
    flexDirection: 'row',
  },
  basis80: { flexBasis: '80%' },
  basis20: { flexBasis: '20%' },
  row_start_center: {
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
  },
  picture: {
    backgroundColor: 'black',
    width: 40,
    height: 40,
    borderRadius: 20,
  },
  ml_20: { marginLeft: 20 },
  username: {
    color: 'black',
    fontWeight: 'bold',
    fontSize: 18,
    fontFamily: 'Inter-Bold',
  },
  desc: { fontFamily: 'Inter-Light' },
  time: {
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
    paddingTop: 15,
    marginLeft: '20%',
  },
  timeText: { fontFamily: 'Inter-Regular', color: Colors.gray.muted },
  btnContainer: {
    flexBasis: '20%',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  acceptBtn: {
    borderRadius: 20,
    borderWidth: 1,
    padding: 10,
    borderColor: '#888BF4',
  },
  acceptText: { color: '#888BF4' },
  declineBtn: {
    borderRadius: 20,
    borderWidth: 1,
    padding: 10,
    borderColor: 'red',
    marginTop: 10,
  },
  declineText: { color: 'red' },
  Topcontainer: { backgroundColor: '#fff', flex: 1 },
});

export default VenueDetails;
