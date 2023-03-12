import { useState, useEffect } from "react";

const useAuth = () => {
  const [user, setUser] = useState(true);
  const [loading, setLoading] = useState(false);

  /*
  this will be used to check for the local storage for the user
  useEffect(() => {
  }, []);
  */

  return { user, loading };
};
