// App.tsx
import { useEffect, useState } from 'react';
import { View, Button, Text } from 'react-native';
import { changeAppIcon,getAppIcon } from 'react-native-dynamic-icon-change';

const App = () => {
  const [currentIcon, setCurrentIcon] = useState<string>('');

  useEffect(() => {
    getAppIcon()
      .then(setCurrentIcon)
      .catch((error) => console.error('Error fetching current icon:', error));
  }, []);

  const switchIcon = (iconName: string) => {
    changeAppIcon(iconName)
      .then(() => {
        setCurrentIcon(iconName);
        console.log('Switched to icon:', iconName);
      })
      .catch(console.error);
  };

  return (
    <View style={{ padding: 20 }}>
      <Text>Current App Icon: {currentIcon}</Text>
      <Button
        title="Switch to Alternate Icon 1"
        onPress={() => switchIcon('AppIcon2')}
      />
      <Button
        title="Switch to Alternate Icon 2"
        onPress={() => switchIcon('AppIcon3')}
      />
      <Button
        title="Revert to Default Icon"
        onPress={() => switchIcon('AppIcon')}
      />
    </View>
  );
};

export default App;
