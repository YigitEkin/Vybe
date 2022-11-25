import { useState, useEffect } from "react";
import { Dimensions } from "react-native";

export const DimensionsHelper = () => {
  const dimensions = {
    window: Dimensions.get("window"),
    screen: Dimensions.get("screen"),
  };

  return { dimensions, isLargerDevice: dimensions.window.width > 375 };
};
