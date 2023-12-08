DESCRIPTION = "SiFive Linux Kernel"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

KBRANCH ?= "linux-6.5.y"
KBRANCH:freedom-u-540 ?= "linux-6.5.y"
KBRANCH:qemuriscv64 ?= "linux-6.5.y"
KBRANCH:unmatched ?= "linux-6.5.y"

SRCREV_machine ?= "43a868577dfcd9be6930453dc5ec557ab2e9ad89"
SRCREV_machine:freedom-u-540 ?= "43a868577dfcd9be6930453dc5ec557ab2e9ad89"
SRCREV_machine:qemuriscv64 ?= "43a868577dfcd9be6930453dc5ec557ab2e9ad89"
SRCREV_machine:unmatched ?= "43a868577dfcd9be6930453dc5ec557ab2e9ad89"
SRCREV_meta ?= "9c7dcb61723601208997d38455d9e6928851e5ff"

KCONFIG_MODE = "--alldefconfig"

KBUILD_DEFCONFIG ?= ""
KBUILD_DEFCONFIG:freedom-u540 ?= "defconfig"
KBUILD_DEFCONFIG:qemuriscv64 ?= "defconfig"
KBUILD_DEFCONFIG:unmatched ?= "defconfig"

KERNEL_EXTRA_FEATURES ?= ""
KERNEL_FEATURES:append:qemuriscv64 = " cfg/virtio.scc"
KERNEL_FEATURES:remove = "cfg/fs/vfat.scc"
KERNEL_FEATURES:remove = "features/debug/printk.scc"
KERNEL_FEATURES:remove = "features/kernel-sample/kernel-sample.scc"

require recipes-kernel/linux/linux-yocto.inc

SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;name=machine;branch=${KBRANCH} \
           git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-6.5;destsuffix=${KMETA}"
SRC_URI:append:riscv64 = " \
    file://0001-riscv-sifive-fu740-cpu-1-2-3-4-set-compatible-to-sif.patch \
    file://0002-riscv-sifive-unmatched-define-PWM-LEDs.patch \
    file://0003-Revert-riscv-dts-sifive-unmatched-Link-the-tmp451-wi.patch \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
LINUX_VERSION ?= "6.5.10"
LINUX_VERSION_EXTENSION = ""

PV = "${LINUX_VERSION}+git${SRCPV}"

KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "2"
KCONF_AUDIT_LEVEL = "2"
KCONF_BSP_AUDIT_LEVEL:qemuriscv64 = "1"
KCONF_AUDIT_LEVEL:qemuriscv64 = "1"

COMPATIBLE_MACHINE = "(freedom-u540|qemuriscv64|unmatched)"