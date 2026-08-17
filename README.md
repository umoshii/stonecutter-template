<div align="center">

![](https://i.pinimg.com/originals/b2/d8/32/b2d83267ffee58244f9a161c1b17b677.gif)
### autumn's fabric stonecutter mod template
wuff

</div>

#

### overview
simple kotlin fabric stonecutter multiversion mod template with support for versions after 26.1.2\
currently supporting: `26.1.2` and `26.2`, with vcs-version set to `26.2`\
includes dev-auth by default

### what to do next?
- change mod metadata in `stonecutter.properties.toml`
- change `rootProject.name` in `gradle.properties`
- change group/package names
- change entrypoints in `fabric.mod.json`
- rename `template.mixins.json` to match your modid
- bark

### what to know
- lang files are written into `src/main/lang` (with object support!), which are then flattened, saved into, and sourced from `versions/<version>/build/generated/resources/assets/<modid>/lang`
- mod deps should always be specified in `stonecutter.properties.toml`
- always refresh to the vcs-version when committing changes to avoid noise in commit diffs

#

<p align="center">by autumn/umoshi 🐾</p>