# SweetMMORPG

仅为 MMOItems 提供法力值、体力值的，最基础的 RPG 核心插件。

## 简介

由于我的服务器不需要那么多花里胡哨的技能、职业系统，安装那些复杂的 RPG 核心插件甚至会影响服务器原本的体验，所以在 wiki 上找到了官方提供的 mmoitems-mana 扩展。但是打开仓库一看… 至少已经有两年没更新了，不太放心这个扩展插件的可用性，而且官方扩展的功能也太少了，根本不够用。  
官方开发文档只丢了一个 MMOItems 开源链接和一些无关紧要的东西，像是 RPG 核心这类东西，我没找到相关文档。本着来都来了的心情，我打算参考官方的扩展自己重写一个只提供 mana 和 stamina 的基础 RPG 核心。并且通过数据库储存这些值，离开服务器时写入，进入服务器时同步。

## 注意事项

大概仅支持 MMOItems 6.10，不保证其它版本可正常使用。  
请注意 MMOItems 默认配置中的 `modifiers/example-modifiers.yml` 和 `item-sets.yml` 均有放大 `max-mana`，使得最高法力值提升的配置。  
另外，不要将本插件与 MMOCore 一齐使用，本插件会覆盖掉 MMOCore 的等级、class、技能等功能。

## 鸣谢

+ [mmopluginteam/mmoitems-mana](https://github.com/mmopluginteam/mmoitems-mana): MMOItems 官方扩展，仅提供 mana 和 stamina 系统 —— No License
