import { jsxs as _jsxs, jsx as _jsx } from "react/jsx-runtime";
// App.tsx
import { useEffect, useState } from 'react';
import { View, Button, Text } from 'react-native';
import { changeAppIcon, getAppIcon } from 'react-native-dynamic-icon-change';
const App = () => {
    const [currentIcon, setCurrentIcon] = useState('');
    useEffect(() => {
        getAppIcon()
            .then(setCurrentIcon)
            .catch((error) => console.error('Error fetching current icon:', error));
    }, []);
    const switchIcon = (iconName) => {
        changeAppIcon(iconName)
            .then(() => {
            setCurrentIcon(iconName);
            console.log('Switched to icon:', iconName);
        })
            .catch(console.error);
    };
    return (_jsxs(View, { style: { padding: 20 }, children: [_jsxs(Text, { children: ["Current App Icon: ", currentIcon] }), _jsx(Button, { title: "Switch to Alternate Icon 1", onPress: () => switchIcon('AppIcon2') }), _jsx(Button, { title: "Switch to Alternate Icon 2", onPress: () => switchIcon('AppIcon3') }), _jsx(Button, { title: "Revert to Default Icon", onPress: () => switchIcon('AppIcon') })] }));
};
export default App;
