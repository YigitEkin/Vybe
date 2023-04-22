import React, { useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  TextInput,
  TouchableOpacity,
} from "react-native";
import { Colors } from "../../../constants/Colors";
import * as Font from "expo-font";
import StyledButton from "../../../components/HomePage/StyledButton";

import { FontAwesome } from "@expo/vector-icons";
import { useNavigation, useRoute } from "@react-navigation/native";

interface StarRatingProps {
  onRatingPress: (rating: number) => void;
}

const StarRating = ({ onRatingPress }: StarRatingProps) => {
  const [rating, setRating] = useState<number>(0);

  const handleRatingPress = (newRating: number) => {
    setRating(newRating);
    onRatingPress(newRating);
  };

  const renderStar = (position: number) => {
    const filledStars = Math.floor(rating);
    const isHalfStar =
      rating - filledStars >= 0.5 &&
      rating - filledStars < 1 &&
      position === filledStars + 1;
    const isFilled = position <= filledStars || isHalfStar;

    return (
      <TouchableOpacity
        key={position}
        onPress={() => handleRatingPress(position)}
      >
        <FontAwesome
          name={isFilled ? (isHalfStar ? "star-half-full" : "star") : "star-o"}
          size={45}
          color={isFilled ? "#f1c40f" : "gray"}
        />
      </TouchableOpacity>
    );
  };

  return (
    <View style={styles.star}>
      {[1, 2, 3, 4, 5].map((position) => renderStar(position))}
    </View>
  );
};

const AddVenueReview = () => {
  const [rating, setRating] = React.useState<null | number>(null);
  const navigation = useNavigation();
  const route = useRoute();
  const { id } = route.params;

  const [fontsLoaded] = Font.useFonts({
    "Inter-Regular": require("../../../assets/fonts/Inter/static/Inter-Regular.ttf"),
    "Inter-Bold": require("../../../assets/fonts/Inter/static/Inter-Bold.ttf"),
  });

  return fontsLoaded ? (
    <View style={styles.container}>
      <Text style={styles.title}>Review the Vybe of this venue!</Text>
      <Text style={styles.ratingText}>Your Rating:</Text>
      <View style={styles.innerContainer}>
        <StarRating onRatingPress={(rating) => setRating(rating)} />
        <Text style={[styles.ratingText, { marginBottom: 20 }]}>
          Comments (Optional)
        </Text>
        <TextInput style={styles.cardInput} multiline={true} />
        <StyledButton
          style={styles.submitButton}
          onPress={() => {
            //TODO: Add review to database
            //TODO: Add review to venue details page
            rating !== null ? navigation.navigate("MapView") : null;
          }}
          buttonText="Submit"
        />
      </View>
    </View>
  ) : null;
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  innerContainer: {
    alignItems: "center",
  },
  title: {
    fontFamily: "Inter-Bold",
    fontSize: 20,
    color: "white",
    textAlign: "center",
  },
  ratingText: {
    fontFamily: "Inter-Regular",
    fontSize: 25,
    color: Colors.gray.muted,
    textAlign: "center",
    marginTop: 30,
  },
  cardInput: {
    backgroundColor: "white",
    width: "90%",
    borderRadius: 40,
    paddingTop: 20,
    paddingBottom: 20,
    paddingLeft: 40,
    paddingRight: 20,
    fontSize: 20,
    fontFamily: "Inter-Regular",
    textAlignVertical: "top",
    overflow: "scroll",
    height: 350,
  },
  commentContainer: {
    width: "97%",
    marginTop: 20,
    flex: 1,
  },
  submitButton: {
    backgroundColor: Colors.purple.dark,
    width: "90%",
    height: 50,
    borderRadius: 25,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 30,
  },
  star: {
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    width: "70%",
    marginTop: 10,
    marginBottom: 20,
  },
});

export default AddVenueReview;
