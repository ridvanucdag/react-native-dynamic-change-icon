import DynamicIconChange from './NativeDynamicIconChange';

export function changeAppIcon(iconName?: string): Promise<string> {
  return DynamicIconChange.changeAppIcon(iconName);
}

export function getAppIcon(): Promise<string> {
  return DynamicIconChange.getAppIcon();
}