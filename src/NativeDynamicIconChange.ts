import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';
export interface Spec extends TurboModule {
  readonly getConstants: () => {};
  changeAppIcon: (iconName?: string) => Promise<string>;
  getAppIcon: () => Promise<string>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('DynamicIconChange');
