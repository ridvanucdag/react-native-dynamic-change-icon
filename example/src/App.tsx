import React, { useEffect, useState } from "react";
import { View, Button, Text } from "react-native";
import { changeAppIcon, getAppIcon } from "react-native-dynamic-icon-change";

const App = () => {
  const [currentIcon, setCurrentIcon] = useState("");

  useEffect(() => {
    getAppIcon().then(setCurrentIcon).catch(console.error);
  }, []);

  const switchIcon = (iconName) => {
    changeAppIcon(iconName)
      .then(() => setCurrentIcon(iconName))
      .catch(console.error);
  };

  return (
    <View>
      <Text>Current Icon: {currentIcon}</Text>
      <Button
        title="Switch to Alternate Icon"
        onPress={() => switchIcon("YourIconName")}
      />
      <Button
        title="Revert to Default Icon"
        onPress={() => switchIcon("Default")}
      />
    </View>
  );
};

export default App;