const fs = require("fs");
const path = require("path");
const pngToIco = require("png-to-ico");

async function main() {
  const source = path.resolve(__dirname, "../../icon.png");
  const outputDir = path.resolve(__dirname, "../build");
  const output = path.join(outputDir, "icon.ico");
  fs.mkdirSync(outputDir, { recursive: true });
  const ico = await pngToIco(source);
  fs.writeFileSync(output, ico);
  console.log("Created Windows icon:", output);
}

main().catch((error) => {
  console.error(error);
  process.exit(1);
});
