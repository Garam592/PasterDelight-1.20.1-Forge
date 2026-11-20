# PasterDelight新回复san系统

## 字段说明

| 字段 | 含义 | 备注 |
|---|---|---|
| 饥饿值 | `FoodProperties.nutrition` | 1 点 = 半格鸡腿（半格饥饿条） |
| 饱和度系数 | `FoodProperties.saturationMod` | `FoodProperties.Builder.saturationMod(float)` |
| 恢复饱食度（有效饱食度） | 实际恢复的饱和度 | = 饥饿值 × 饱和度系数 × 2 |

---

## 背景

> - 旧版 pasterdream 与农夫乐事的联动只是单纯给每种食物添加了 +1 san，这使得 san 的回复量与饱食度完全不沾边，玩家会因为制作性价比问题去选择制作简单的食物来恢复 san。
> - 因此，pasterdelight 使用与 pasterdream 旧版截然不同的一套系统，它会根据食物的饱食度来提供不同的回 san 值与料理 buff，且特性完全由 tag 控制。魔改作者既可以通过魔改食物饥饿度、增删 tag，也可以通过下方配置项来对整套系统进行配置。

## 配置项

所有数值均可在 `config/pasterdelight-common.toml` 中调整，默认值如下：

| 配置键 | 含义 | 默认值 |
|---|---|---|
| `san_per_saturation` | 每多少饱和度回复 1 san | 6.0 |
| `min_effective_saturation_for_san` | 回复 san 所需的最小饱和度 | 6.0 |
| `max_effective_saturation_for_san` | 回复 san 的封顶饱和度 | 30.0 |
| `cook_base_seconds` | 料理 buff 初始时长（秒） | 30 |
| `cook_saturation_per_step` | 每多少饱和度增加一次料理时长 | 5 |
| `cook_seconds_per_step` | 每次增加的料理时长（秒） | 30 |
| `cook_saturation_per_level` | 每多少饱和度提升一级料理等级 | 15 |

---

## `pasterdelight:recover_sanity` 标签（恢复 san 系统）

> 该标签在 `ModItemTagsProvider` 中由 `addOptionalTag` 写入，包含 `farmersdelight:meals`（30 种）与 `farmersdelight:snacks`（13 种），共 **43 种**。
>
> 恢复 san 规则（默认值）：
> - 恢复饱食度（有效饱食度）= 2 × 饥饿值 × 饱和度系数
> - 恢复 san = min(恢复饱食度, `max_effective_saturation_for_san`) / `san_per_saturation` = min(恢复饱食度, 30) / 6
> - 恢复饱食度 < `min_effective_saturation_for_san`（6）时不恢复，表中记为 `-`

## `pasterdelight:give_cook_buff` 标签（动态料理 buff 系统）

> 该标签在 `ModItemTagsProvider` 中由 `addOptionalTag` 写入，包含 `farmersdelight:snacks`（13 种）。
>
> 料理 buff 规则（默认值）：
> - buff 时长（秒）= `cook_base_seconds` + `cook_seconds_per_step` × ⌊恢复饱食度 / `cook_saturation_per_step`⌋ = 30 + 30 × ⌊恢复饱食度 / 5⌋
> - buff 等级 = ⌊恢复饱食度 / `cook_saturation_per_level`⌋ = ⌊恢复饱食度 / 15⌋；代码中 amplifier 从 0 起算（0 = Cook I，1 = Cook II）

---

### meals（30 种，仅恢复 san）

| 食物 | 饥饿值 | 恢复饱食度 | 恢复san |
|---|---|---|---|
| 蘑菇煲 mushroom_stew | 6 | 7.2 | 1.2 |
| 甜菜汤 beetroot_soup | 6 | 7.2 | 1.2 |
| 兔肉煲 rabbit_stew | 10 | 12.0 | 2.0 |
| 混合沙拉 mixed_salad | 6 | 7.2 | 1.2 |
| 米饭 cooked_rice | 6 | 4.8 | - |
| 大骨汤 bone_broth | 8 | 11.2 | 1.87 |
| 牛肉炖 beef_stew | 12 | 19.2 | 3.2 |
| 蔬菜汤 vegetable_soup | 12 | 19.2 | 3.2 |
| 鱼肉炖 fish_stew | 12 | 19.2 | 3.2 |
| 鸡肉汤 chicken_soup | 14 | 21.0 | 3.5 |
| 炒饭 fried_rice | 14 | 21.0 | 3.5 |
| 南瓜汤 pumpkin_soup | 14 | 21.0 | 3.5 |
| 烘焙鳕鱼炖 baked_cod_stew | 14 | 21.0 | 3.5 |
| 面条汤 noodle_soup | 14 | 21.0 | 3.5 |
| 洋葱汤 onion_soup | 12 | 19.2 | 3.2 |
| 培根鸡蛋 bacon_and_eggs | 10 | 12.0 | 2.0 |
| 蔬菜杂烩 ratatouille | 10 | 12.0 | 2.0 |
| 牛排配土豆 steak_and_potatoes | 12 | 19.2 | 3.2 |
| 肉丸意面 pasta_with_meatballs | 12 | 19.2 | 3.2 |
| 羊排意面 pasta_with_mutton_chop | 12 | 19.2 | 3.2 |
| 蘑菇饭 mushroom_rice | 12 | 19.2 | 3.2 |
| 烤羊排 roasted_mutton_chops | 14 | 21.0 | 3.5 |
| 蔬菜面 vegetable_noodles | 14 | 21.0 | 3.5 |
| 鱿鱼墨面 squid_ink_pasta | 14 | 21.0 | 3.5 |
| 香烤鲑鱼 grilled_salmon | 14 | 21.0 | 3.5 |
| 盘装烤鸡 roast_chicken | 14 | 21.0 | 3.5 |
| 碗装填馅南瓜 stuffed_pumpkin | 14 | 21.0 | 3.5 |
| 盘装蜜汁火腿 honey_glazed_ham | 14 | 21.0 | 3.5 |
| 盘装牧羊人派 shepherds_pie | 14 | 21.0 | 3.5 |
| 闪光沙拉 gleaming_salad | 14 | 21.0 | 3.5 |

### snacks（13 种，恢复 san + 料理 buff）

| 食物 | 饥饿值 | 恢复饱食度 | 恢复san | 料理时长 | 料理等级 |
|---|---|---|---|---|---|
| 烧烤串 barbecue_stick | 8 | 14.4 | 2.4 | 90秒 | I |
| 鸡蛋三明治 egg_sandwich | 8 | 12.8 | 2.13 | 90秒 | I |
| 鸡肉三明治 chicken_sandwich | 10 | 16.0 | 2.67 | 120秒 | II |
| 汉堡包 hamburger | 11 | 17.6 | 2.93 | 120秒 | II |
| 培根三明治 bacon_sandwich | 10 | 16.0 | 2.67 | 120秒 | II |
| 羊肉卷饼 mutton_wrap | 10 | 16.0 | 2.67 | 120秒 | II |
| 饺子 dumplings | 8 | 12.8 | 2.13 | 90秒 | I |
| 填馅马铃薯 stuffed_potato | 10 | 14.0 | 2.33 | 90秒 | I |
| 卷心菜卷 cabbage_rolls | 5 | 5.0 | - | 60秒 | I |
| 鲑鱼寿司 salmon_roll | 7 | 8.4 | 1.4 | 60秒 | I |
| 鳕鱼寿司 cod_roll | 7 | 8.4 | 1.4 | 60秒 | I |
| 海带寿司卷 kelp_roll | 12 | 14.4 | 2.4 | 90秒 | I |
| 海带寿司 kelp_roll_slice | 6 | 6.0 | 1.0 | 60秒 | I |

### 不恢复 san 的食物（恢复饱食度 < 6）

| 食物 | 饥饿值 | 恢复饱食度 |
|---|---|---|
| 米饭 cooked_rice | 6 | 4.8 |
| 卷心菜卷 cabbage_rolls | 5 | 5.0 |
