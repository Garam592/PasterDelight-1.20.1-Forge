## 字段说明

| 字段 | 含义 | 备注 |
|---|---|---|
| 饥饿值 | `FoodProperties.nutrition` | 1 点 = 半格鸡腿（半格饥饿条） |
| 饱和度系数 | `FoodProperties.saturationMod` | `FoodProperties.Builder.saturationMod(float)` |
| 饱和恢复 | 实际恢复的饱和度 | = 饥饿值 × 饱和度系数 × 2 |
---

## `pasterdelight:pasterdelight_recover_sanity` 标签（恢复 san 系统）

> 旧版pasterdream与农夫乐事的联动只是单纯给每种食物添加了+1san，这使得san的回复量与饥饿度完全不沾边，玩家会因为食物制作性价比问题去选择制作简单的食物来恢复san
> 
> 因此，pasterdelight使用与pasterdream旧版截然不同的一套系统，它会根据制作食物的饱食度来提供不同的回san值，且特性完全由tag来控制，魔改作者完全可以通过魔改饥饿度甚至删除增添tag来对该系统进行配置
> 
> 该标签在 `ModItemTagsProvider` 中由 `addOptionalTag` 写入，包含 `farmersdelight:meals`（30 种）与 `farmersdelight:snacks`（13 种），共 **43 种**。
>
> 恢复 san 规则（`TagsFoodSanHandler`）：
> - 恢复饱食度（有效饱食度）= 2 × 饥饿值 × 饱和度系数
> - 恢复 san = 恢复饱食度 / 4.8
> - 恢复饱食度 < 6 时不恢复，表中记为 `-`

### meals（30 种）

| 食物 | 饥饿值 | 恢复饱食度 | 恢复san |
|---|---|---|---|
| 蘑菇煲 mushroom_stew | 6 | 7.2 | 1.5 |
| 甜菜汤 beetroot_soup | 6 | 7.2 | 1.5 |
| 兔肉煲 rabbit_stew | 10 | 12.0 | 2.5 |
| 混合沙拉 mixed_salad | 6 | 7.2 | 1.5 |
| 米饭 cooked_rice | 6 | 4.8 | - |
| 大骨汤 bone_broth | 8 | 11.2 | 2.33 |
| 牛肉炖 beef_stew | 12 | 19.2 | 4.0 |
| 蔬菜汤 vegetable_soup | 12 | 19.2 | 4.0 |
| 鱼肉炖 fish_stew | 12 | 19.2 | 4.0 |
| 鸡肉汤 chicken_soup | 14 | 21.0 | 4.38 |
| 炒饭 fried_rice | 14 | 21.0 | 4.38 |
| 南瓜汤 pumpkin_soup | 14 | 21.0 | 4.38 |
| 烘焙鳕鱼炖 baked_cod_stew | 14 | 21.0 | 4.38 |
| 面条汤 noodle_soup | 14 | 21.0 | 4.38 |
| 洋葱汤 onion_soup | 12 | 19.2 | 4.0 |
| 培根鸡蛋 bacon_and_eggs | 10 | 12.0 | 2.5 |
| 蔬菜杂烩 ratatouille | 10 | 12.0 | 2.5 |
| 牛排配土豆 steak_and_potatoes | 12 | 19.2 | 4.0 |
| 肉丸意面 pasta_with_meatballs | 12 | 19.2 | 4.0 |
| 羊排意面 pasta_with_mutton_chop | 12 | 19.2 | 4.0 |
| 蘑菇饭 mushroom_rice | 12 | 19.2 | 4.0 |
| 烤羊排 roasted_mutton_chops | 14 | 21.0 | 4.38 |
| 蔬菜面 vegetable_noodles | 14 | 21.0 | 4.38 |
| 鱿鱼墨面 squid_ink_pasta | 14 | 21.0 | 4.38 |
| 香烤鲑鱼 grilled_salmon | 14 | 21.0 | 4.38 |
| 盘装烤鸡 roast_chicken | 14 | 21.0 | 4.38 |
| 碗装填馅南瓜 stuffed_pumpkin | 14 | 21.0 | 4.38 |
| 盘装蜜汁火腿 honey_glazed_ham | 14 | 21.0 | 4.38 |
| 盘装牧羊人派 shepherds_pie | 14 | 21.0 | 4.38 |
| 闪光沙拉 gleaming_salad | 14 | 21.0 | 4.38 |

### snacks（13 种）

| 食物 | 饥饿值 | 恢复饱食度 | 恢复san |
|---|---|---|---|
| 烧烤串 barbecue_stick | 8 | 14.4 | 3.0 |
| 鸡蛋三明治 egg_sandwich | 8 | 12.8 | 2.67 |
| 鸡肉三明治 chicken_sandwich | 10 | 16.0 | 3.33 |
| 汉堡包 hamburger | 11 | 17.6 | 3.67 |
| 培根三明治 bacon_sandwich | 10 | 16.0 | 3.33 |
| 羊肉卷饼 mutton_wrap | 10 | 16.0 | 3.33 |
| 饺子 dumplings | 8 | 12.8 | 2.67 |
| 填馅马铃薯 stuffed_potato | 10 | 14.0 | 2.92 |
| 卷心菜卷 cabbage_rolls | 5 | 5.0 | - |
| 鲑鱼寿司 salmon_roll | 7 | 8.4 | 1.75 |
| 鳕鱼寿司 cod_roll | 7 | 8.4 | 1.75 |
| 海带寿司卷 kelp_roll | 12 | 14.4 | 3.0 |
| 海带寿司 kelp_roll_slice | 6 | 6.0 | 1.25 |

### 不恢复 san 的食物（恢复饱食度 < 6）

| 食物 | 饥饿值 | 恢复饱食度 |
|---|---|---|
| 米饭 cooked_rice | 6 | 4.8 |
| 卷心菜卷 cabbage_rolls | 5 | 5.0 |
