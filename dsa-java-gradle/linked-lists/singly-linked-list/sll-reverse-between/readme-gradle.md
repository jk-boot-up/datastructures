# README - Gradle Sources & IntelliJ Navigation

This document explains helper Gradle tasks and steps to download/attach **Gradle API** and dependency sources so IntelliJ can navigate into Gradle classes (e.g., `org.gradle.api.plugins.ExtensionAware`) and show dependency sources.

---

## What this contains

Two helper Gradle tasks (add to your `build.gradle` if not already present):

1. `downloadGradleSources`  
   - Downloads and unzips the Gradle `<wrapper-version>-all.zip` distribution into `build/gradle-dist/gradle-<version>/`.  
   - Useful to attach Gradle API sources to IntelliJ manually.

2. `downloadDependencySources` (modern)  
   - Scans resolved configurations (`compileClasspath`, `runtimeClasspath`, `testCompileClasspath`) and attempts to download `*-sources.jar` for each dependency.  
   - Unpacks available sources into `build/dependency-sources/<group>.<name>-<version>/`.

---

## How to run the helper tasks

From project root:

```bash
# Download and extract the Gradle distribution sources for the wrapper version
./gradlew downloadGradleSources

# Attempt to download and unpack dependency sources
./gradlew downloadDependencySources
```

Outputs:
- Gradle dist extracted to: `build/gradle-dist/gradle-<version>/`
- Dependency sources (if found) unpacked to: `build/dependency-sources/`

---

## Attach Gradle API sources in IntelliJ (manual steps)

If IntelliJ still doesn't navigate into `org.gradle.*` classes after enabling "Download sources", attach the Gradle API sources manually:

1. Run:
   ```bash
   ./gradlew downloadGradleSources
   ```
2. In IntelliJ, open **Project** view → **External Libraries**.
3. Find the Gradle API library entry (or the Gradle distribution entry).
4. Right-click the library → **Library Properties** (or select the JAR and choose *Attach Sources*).
5. Point to the extracted Gradle distribution folder, for example:
   ```
   build/gradle-dist/gradle-9.0.0/src/
   ```
   or simply select the unzipped distribution root `build/gradle-dist/gradle-9.0.0/` — IntelliJ will search inside for sources.
6. After attaching, try **Ctrl/Cmd+B** on `ExtensionAware` or any `org.gradle...` class.

---

## Tip: Let IntelliJ fetch dependency sources automatically

- In IntelliJ: **File → Settings → Build Tools → Gradle**  
  - Set **Use Gradle from** → *Gradle wrapper*  
  - Enable **Download sources** and **Download external documentation**  
- Then click **Reload All Gradle Projects** in the Gradle tool window. IntelliJ will try to download available `*-sources.jar` artifacts and attach them.

---

## If you're using the helper tasks in `build.gradle`

Below are the task names and what they do — paste them into your `build.gradle` if needed:

- `downloadGradleSources` — download + unzip Gradle `<version>-all.zip` into `build/gradle-dist/`.
- `downloadDependencySources` — attempt to download `*-sources.jar` for resolved dependencies and unpack into `build/dependency-sources/`.

---

## Troubleshooting

- **No sources found for some dependencies** — many third-party libraries do not publish `-sources.jar`. The `downloadDependencySources` task will log which artifacts had no sources.
- **Gradle API sources still not attached** — ensure you used `-all.zip` (it contains more resources) and point IntelliJ to the unzipped folder. If needed, attach the `src` folder or `*-sources.zip` explicitly.
- **Gradle network or proxy problems** — run `./gradlew --refresh-dependencies` or configure proxy settings via `~/.gradle/gradle.properties`.

---

## Example commands summary

```bash
# refresh dependencies and force Gradle to resolve
./gradlew --refresh-dependencies

# download Gradle distribution sources (wrapper version)
./gradlew downloadGradleSources

# download & unpack dependency sources (if available)
./gradlew downloadDependencySources
```

---

## References

- Gradle distributions: https://services.gradle.org/distributions/
- Gradle docs: https://docs.gradle.org
- IntelliJ Gradle integration docs: https://www.jetbrains.com/help/idea/gradle.html

---

Created for: sll-reverse-between project
