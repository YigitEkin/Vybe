import React, { useState } from "react";
import HomeCheckedIn from "./HomeCheckedIn";
import HomeNotCheckedIn from "./HomeNotCheckedIn";
import { useCheckedInStore } from "../../stores/CheckedInStore";

const HomePageWrapper = () => {
  const { isCheckIn } = useCheckedInStore();

  return isCheckIn ? <HomeCheckedIn /> : <HomeNotCheckedIn />;
};

export default HomePageWrapper;
