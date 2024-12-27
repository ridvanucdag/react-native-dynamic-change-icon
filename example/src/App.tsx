import { useState } from 'react';
import { Text, View, StyleSheet, Button } from 'react-native';
import { changeAppIcon, getAppIcon } from 'react-native-dynamic-icon-change';
// import { iconChange } from '../iconChange';

export default function App() {
  const [currentIcon, setCurrentIcon] = useState<string | null>(null);

  const switchIcon = async (iconName: string) => {
    try {
      await changeAppIcon(iconName);
      setCurrentIcon(iconName);
    } catch (error) {
      console.error(error);
    }
  };

  const fetchCurrentIcon = async () => {
    console.log('getAppIcon', getAppIcon);
    try {
      const icon = await getAppIcon();
      setCurrentIcon(icon);
    } catch (error) {
      console.error(error);
    }
  };

  // useEffect(() => {
  //   iconChange(1);
  // });

  return (
    <View style={styles.container}>
      <Text style={styles.text}>
        Current Icon Name: {currentIcon ?? 'Not yet received'}
      </Text>

      <Button
        title="Change Icon to AppIcon3"
        onPress={() => switchIcon('AppIcon2')}
      />
      <Button
        title="Change Icon to AppIcon3"
        onPress={() => switchIcon('AppIcon3')}
      />
      <Button
        title="Change Icon to AppIcon4"
        onPress={() => switchIcon('Icon4')}
      />
      {/* <Button title="Return to Default Icon" onPress={() => switchIcon(null)} /> */}
      <Button title="Get Current Icon" onPress={fetchCurrentIcon} />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },
  text: {
    fontSize: 16,
    marginBottom: 10,
  },
});
