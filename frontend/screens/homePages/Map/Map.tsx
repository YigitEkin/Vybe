import React, { useEffect, useState } from 'react';
import {
  StyleSheet,
  Text,
  TextInput,
  View,
  ScrollView,
  Animated,
  Image,
  TouchableOpacity,
  Dimensions,
  Platform,
  Pressable,
  TouchableWithoutFeedback,
  Keyboard,
  ActivityIndicator,
} from 'react-native';
import { FontAwesome } from '@expo/vector-icons';
import MapView, {
  PROVIDER_GOOGLE,
  Marker,
  PROVIDER_DEFAULT,
} from 'react-native-maps';
import SearchBarMap from '../../../components/HomePage/SearchBarMap';
import * as Location from 'expo-location';
import * as Permissions from 'expo-permissions';
// @ts-ignore
import Ionicons from 'react-native-vector-icons/Ionicons';
// @ts-ignore
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
// @ts-ignore
import Fontisto from 'react-native-vector-icons/Fontisto';
// @ts-ignore
import StarRating from '../components/StarRating';

import { useNavigation, useTheme } from '@react-navigation/native';
import { Colors } from '../../../constants/Colors';
import Splash from '../../Splash';
import axios from 'axios';

const { width, height } = Dimensions.get('window');
const CARD_HEIGHT = 220;
const CARD_WIDTH = width * 0.8;
const SPACING_FOR_CARD_INSET = width * 0.1 - 10;
const LATITUDE_DELTA = 0.0922;
const LONGITUDE_DELTA = LATITUDE_DELTA * (width / height);
const ANIMATION_TIMEOUT = 800;
const Images = [
  { image: require('../../../assets/icon.png') },
  { image: require('../../../assets/mapPin.png') },
  { image: require('../../../assets/icon.png') },
  { image: require('../../../assets/icon.png') },
];

export type MapItem = {
  coordinate: {
    latitude: number;
    longitude: number;
    latitudeDelta: number;
    longitudeDelta: number;
  };
  title: string;
  description: string;
  image: any;
  rating: number;
  reviews: number;
  id: number;
};

const markers: MapItem[] = [
  {
    coordinate: {
      latitude: 39.87816801608047,
      longitude: 32.70768330187889,
      latitudeDelta: LATITUDE_DELTA,
      longitudeDelta: LONGITUDE_DELTA,
    },
    title: 'Beysu Star',
    description: 'Beysu Star',
    image: Images[0].image,
    rating: 4,
    reviews: 99,
    id: 1,
  },
  {
    coordinate: {
      latitude: 39.88987990516257,
      longitude: 32.75868199086349,
      latitudeDelta: LATITUDE_DELTA,
      longitudeDelta: LONGITUDE_DELTA,
    },
    title: 'Federal Coffee Bilkent',
    description: 'FEDO FEDO FEDO',
    image: Images[0].image,
    rating: 4,
    reviews: 99,
    id: 2,
  },
  {
    coordinate: {
      latitude: 39.88355729305515,
      longitude: 32.755733248273344,
      latitudeDelta: LATITUDE_DELTA,
      longitudeDelta: LONGITUDE_DELTA,
    },
    title: 'Bluejay Coffee House',
    description: 'FEDO FEDO FEDO',
    image: Images[0].image,
    rating: 4,
    reviews: 99,
    id: 3,
  },
  {
    coordinate: {
      latitude: 40,
      longitude: 32.75,
      latitudeDelta: LATITUDE_DELTA,
      longitudeDelta: LONGITUDE_DELTA,
    },
    title: '2. mekan',
    description: '2. mekan',
    image: Images[0].image,
    rating: 4,
    reviews: 99,
    id: 4,
  },
  {
    coordinate: {
      latitude: 39.79,
      longitude: 32.7,
      latitudeDelta: LATITUDE_DELTA,
      longitudeDelta: LONGITUDE_DELTA,
    },
    title: '3. mekan',
    description: '3. mekan',
    image: Images[0].image,
    rating: 4,
    reviews: 99,
    id: 5,
  },
  {
    coordinate: {
      latitude: 39.5,
      longitude: 32.8,
      latitudeDelta: LATITUDE_DELTA,
      longitudeDelta: LONGITUDE_DELTA,
    },
    title: '4.mekan',
    description: '4.mekan',
    image: Images[0].image,
    rating: 4,
    reviews: 99,
    id: 6,
  },
];

export const mapDarkStyle = [
  {
    elementType: 'geometry',
    stylers: [
      {
        color: '#212121',
      },
    ],
  },
  {
    elementType: 'labels.icon',
    stylers: [
      {
        visibility: 'off',
      },
    ],
  },
  {
    elementType: 'labels.text.fill',
    stylers: [
      {
        color: '#757575',
      },
    ],
  },
  {
    elementType: 'labels.text.stroke',
    stylers: [
      {
        color: '#212121',
      },
    ],
  },
  {
    featureType: 'administrative',
    elementType: 'geometry',
    stylers: [
      {
        color: '#757575',
      },
    ],
  },
  {
    featureType: 'administrative.country',
    elementType: 'labels.text.fill',
    stylers: [
      {
        color: '#9e9e9e',
      },
    ],
  },
  {
    featureType: 'administrative.land_parcel',
    stylers: [
      {
        visibility: 'off',
      },
    ],
  },
  {
    featureType: 'administrative.locality',
    elementType: 'labels.text.fill',
    stylers: [
      {
        color: '#bdbdbd',
      },
    ],
  },
  {
    featureType: 'poi',
    elementType: 'labels.text.fill',
    stylers: [
      {
        color: '#757575',
      },
    ],
  },
  {
    featureType: 'poi.park',
    elementType: 'geometry',
    stylers: [
      {
        color: '#181818',
      },
    ],
  },
  {
    featureType: 'poi.park',
    elementType: 'labels.text.fill',
    stylers: [
      {
        color: '#616161',
      },
    ],
  },
  {
    featureType: 'poi.park',
    elementType: 'labels.text.stroke',
    stylers: [
      {
        color: '#1b1b1b',
      },
    ],
  },
  {
    featureType: 'road',
    elementType: 'geometry.fill',
    stylers: [
      {
        color: '#2c2c2c',
      },
    ],
  },
  {
    featureType: 'road',
    elementType: 'labels.text.fill',
    stylers: [
      {
        color: '#8a8a8a',
      },
    ],
  },
  {
    featureType: 'road.arterial',
    elementType: 'geometry',
    stylers: [
      {
        color: '#373737',
      },
    ],
  },
  {
    featureType: 'road.highway',
    elementType: 'geometry',
    stylers: [
      {
        color: '#3c3c3c',
      },
    ],
  },
  {
    featureType: 'road.highway.controlled_access',
    elementType: 'geometry',
    stylers: [
      {
        color: '#4e4e4e',
      },
    ],
  },
  {
    featureType: 'road.local',
    elementType: 'labels.text.fill',
    stylers: [
      {
        color: '#616161',
      },
    ],
  },
  {
    featureType: 'transit',
    elementType: 'labels.text.fill',
    stylers: [
      {
        color: '#757575',
      },
    ],
  },
  {
    featureType: 'water',
    elementType: 'geometry',
    stylers: [
      {
        color: '#000000',
      },
    ],
  },
  {
    featureType: 'water',
    elementType: 'labels.text.fill',
    stylers: [
      {
        color: '#3d3d3d',
      },
    ],
  },
];

export const mapStandardStyle = [
  {
    elementType: 'labels.icon',
    stylers: [
      {
        visibility: 'off',
      },
    ],
  },
];
const DismissKeyboard = ({ children }) => (
  <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
    {children}
  </TouchableWithoutFeedback>
);
const MapPage = () => {
  const theme = useTheme();

  const [location, setLocation] = useState<Location.LocationObject | null>(
    null
  );
  const [errorMsg, setErrorMsg] = useState<string | null>(null);
  const [success, setSuccess] = useState<boolean>(false);
  const [markersRes, setMarkersRes] = useState([]);

  useEffect(() => {
    axios
      .get('http://172.20.10.4:8080/api/venues')
      .then((res) => {
        //console.log(res.data);
        let data = res.data;
        let markersRes = data.map((venue) => {
          return {
            coordinate: {
              latitude: parseFloat(venue.location.split(',')[0]),
              longitude: parseFloat(venue.location.split(',')[1]),
              latitudeDelta: LATITUDE_DELTA,
              longitudeDelta: LONGITUDE_DELTA,
            },
            title: venue.name,
            description: venue.description,
            image: Images[0].image,
            rating: 4,
            reviews: 99,
            id: venue.id,
          };
        });
        console.log(markersRes);
        setMarkersRes(markersRes);
        let apiMapState = {
          markersRes,
        };
        setState(apiMapState);
      })
      .catch((e) => e.message);
  }, []);

  useEffect(() => {
    (async () => {
      let { status } = await Location.requestForegroundPermissionsAsync();
      if (status !== 'granted') {
        setErrorMsg('Permission to access location was denied');
        setSuccess(false);
        return;
      }

      let location = await Location.getCurrentPositionAsync({});
      setLocation(location);
      setSuccess(true);
    })();
  }, []);

  const initialMapState = {
    markersRes,
  };

  const [state, setState] = React.useState(initialMapState);
  const [mapIndex, setMapIndex] = React.useState(0);
  const [searchPhrase, setSearchPhrase] = useState('');
  const [clicked, setClicked] = useState(false);

  let mapAnimation = new Animated.Value(0);
  const navigation = useNavigation();

  useEffect(() => {
    mapAnimation.addListener(({ value }) => {
      let index = Math.floor(value / CARD_WIDTH + 0.3); // animate 30% away from landing on the next item
      if (index >= state.markersRes.length) {
        index = state.markersRes.length - 1;
      }
      if (index <= 0) {
        index = 0;
      }
      if (mapIndex !== index) {
        setMapIndex(index);
        const { coordinate } = state.markersRes[index];
        _map.current &&
          _map.current.animateToRegion(
            {
              ...coordinate,
            },
            ANIMATION_TIMEOUT
          );
      }
      // Timeout'lu daha mantıklı aslında da niyeyse çalışmadı
      /*const regionTimeout = setTimeout(() => {
        if (mapIndex !== index) {
          setMapIndex(index);
          const { coordinate } = state.markersRes[index];
          _map.current &&
            _map.current.animateToRegion(
              {
                ...coordinate,
              },
              ANIMATION_TIMEOUT
            );
        }
      }, 10);
      clearTimeout(regionTimeout);*/
    });
  });

  const interpolations = state.markersRes.map((marker: any, index: number) => {
    const inputRange = [
      (index - 1) * CARD_WIDTH,
      index * CARD_WIDTH,
      (index + 1) * CARD_WIDTH,
    ];

    const scale = mapAnimation.interpolate({
      inputRange,
      outputRange: [1, 1.5, 1],
      extrapolate: 'clamp',
    });

    return { scale };
  });

  const onMarkerPress = (mapEventData: any, markerID: number) => {
    const coordinate = mapEventData._targetInst.memoizedProps.coordinate;

    let x = markerID * CARD_WIDTH + markerID * 20;
    if (Platform.OS === 'ios') {
      x = x - SPACING_FOR_CARD_INSET;
    }

    _map.current.animateToRegion(coordinate, ANIMATION_TIMEOUT);
    _scrollView.current.scrollTo({ x: x, y: 0, animated: true });
    setMapIndex(markerID);
  };

  const _map = React.useRef(null);
  const _scrollView = React.useRef(null);

  return success ? (
    <DismissKeyboard>
      <View style={styles.container}>
        <MapView
          ref={_map}
          initialRegion={{
            latitude: location!.coords!.latitude,
            longitude: location!.coords!.longitude,
            latitudeDelta: LATITUDE_DELTA,
            longitudeDelta: LONGITUDE_DELTA,
          }}
          style={styles.container}
          //provider={PROVIDER_GOOGLE}
          customMapStyle={mapDarkStyle}
        >
          {
            // @ts-ignore
            state.markersRes.map((marker: any, index: number) => {
              const scaleStyle = {
                transform: [
                  {
                    scale: interpolations[index].scale,
                  },
                ],
              };
              return (
                // @ts-ignore
                <Marker
                  key={index}
                  coordinate={marker.coordinate}
                  onPress={(e) => {
                    onMarkerPress(e, index);
                  }}
                >
                  <Animated.View style={[styles.markerWrap]}>
                    <Animated.Image
                      source={require('../../../assets/mapPinPurple.png')}
                      // @ts-ignore
                      style={[styles.marker, scaleStyle]}
                      resizeMode='contain'
                    />
                  </Animated.View>
                </Marker>
              );
            })
          }
        </MapView>
        <View style={styles.searchBox}>
          {/*<TextInput
          placeholder='Search here'
          placeholderTextColor='#000'
          autoCapitalize='none'
          style={{ flex: 1, padding: 0 }}
        />*/}
          {/*<Ionicons name='ios-search' size={20} />*/}
          <SearchBarMap
            clicked={clicked}
            setClicked={setClicked}
            searchPhrase={searchPhrase}
            setSearchPhrase={setSearchPhrase}
          />
        </View>

        <Animated.ScrollView
          ref={_scrollView}
          horizontal
          pagingEnabled
          scrollEventThrottle={1}
          showsHorizontalScrollIndicator={false}
          snapToInterval={CARD_WIDTH + 20}
          snapToAlignment='center'
          style={styles.scrollView}
          contentInset={{
            top: 0,
            left: SPACING_FOR_CARD_INSET,
            bottom: 0,
            right: SPACING_FOR_CARD_INSET,
          }}
          contentContainerStyle={{
            paddingHorizontal:
              Platform.OS === 'android' ? SPACING_FOR_CARD_INSET : 0,
          }}
          onScroll={Animated.event(
            [
              {
                nativeEvent: {
                  contentOffset: {
                    x: mapAnimation,
                  },
                },
              },
            ],
            { useNativeDriver: true }
          )}
        >
          {state.markersRes.map((marker: any, index: number) => (
            <Pressable
              style={styles.card}
              key={index}
              onPress={() => {
                /**
                _map.current!.animateToRegion(
                marker.coordinate,
                ANIMATION_TIMEOUT
              );
              setMapIndex(index);
               */
                navigation.navigate(
                  // @ts-ignore
                  'VenueDetail',
                  {
                    id: marker.id,
                  }
                );
              }}
            >
              <Image
                source={marker.image}
                // @ts-ignore
                style={styles.cardImage}
                resizeMode='cover'
              />
              <View style={styles.textContent}>
                <Text numberOfLines={1} style={styles.cardtitle}>
                  {marker.title}
                </Text>

                <View
                  style={{
                    flexDirection: 'row',
                    marginTop: 'auto',
                    marginBottom: 20,
                  }}
                >
                  <Text style={styles.cardDescription}>
                    🎧 {marker.description}
                  </Text>
                  <FontAwesome
                    style={{ marginLeft: 'auto' }}
                    name={
                      marker.rating >= 1
                        ? 'star'
                        : marker.rating >= 0.5
                        ? 'star-half-o'
                        : 'star-o'
                    }
                    size={20}
                    color={'#f1c40f'}
                  />
                  <Text
                    style={{
                      fontSize: 20,
                      color: '#fff',
                      marginLeft: 5,
                      fontWeight: 'bold',
                    }}
                  >
                    4.65
                  </Text>
                </View>
              </View>
            </Pressable>
          ))}
        </Animated.ScrollView>
      </View>
    </DismissKeyboard>
  ) : (
    <View
      style={{
        backgroundColor: '#000',
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <ActivityIndicator size='large' color='#EA34C9' />
    </View>
  );
};

export default MapPage;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#000',
  },
  searchBox: {
    position: 'absolute',
    marginTop: 20,
    flexDirection: 'row',
    //backgroundColor: '#fff',
    //width: '90%',
    alignSelf: 'center',
    borderRadius: 5,
    //padding: 10,
    //shadowColor: '#ccc',
    //shadowOffset: { width: 0, height: 3 },
    //shadowOpacity: 0.5,
    //shadowRadius: 5,
    elevation: 10,
  },
  chipsScrollView: {
    position: 'absolute',
    top: Platform.OS === 'ios' ? 90 : 80,
    paddingHorizontal: 10,
  },
  chipsIcon: {
    marginRight: 5,
  },
  chipsItem: {
    flexDirection: 'row',
    backgroundColor: '#fff',
    borderRadius: 20,
    padding: 8,
    paddingHorizontal: 20,
    marginHorizontal: 10,
    height: 35,
    shadowColor: '#ccc',
    shadowOffset: { width: 0, height: 3 },
    shadowOpacity: 0.5,
    shadowRadius: 5,
    elevation: 10,
  },
  scrollView: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    paddingVertical: 60,
  },
  endPadding: {
    paddingRight: width - CARD_WIDTH,
  },
  card: {
    // padding: 10,
    elevation: 2,
    backgroundColor: '#FFF',
    borderTopLeftRadius: 5,
    borderTopRightRadius: 5,
    marginHorizontal: 10,
    shadowColor: '#000',
    shadowRadius: 5,
    shadowOpacity: 0.3,
    shadowOffset: {
      // @ts-ignore
      x: 2,
      y: -2,
    },
    height: CARD_HEIGHT,
    width: CARD_WIDTH,
    overflow: 'hidden',
  },
  cardImage: {
    flex: 3,
    width: '100%',
    height: '100%',
    alignSelf: 'center',
  },
  textContent: {
    flex: 2,
    padding: 10,
    backgroundColor: '#000',
  },
  cardtitle: {
    fontSize: 16,
    color: '#fff',
  },
  cardDescription: {
    fontSize: 12,
    color: Colors.gray.muted,
    textAlign: 'center',
    paddingTop: 5,
  },
  markerWrap: {
    alignItems: 'center',
    justifyContent: 'center',
    width: 50,
    height: 50,
  },
  marker: {
    width: 25,
    height: 25,
  },
  button: {
    alignItems: 'center',
    marginTop: 5,
  },
  signIn: {
    width: '100%',
    padding: 5,
    justifyContent: 'center',
    alignItems: 'center',
    borderRadius: 3,
  },
  textSign: {
    fontSize: 14,
    fontWeight: 'bold',
  },
});
