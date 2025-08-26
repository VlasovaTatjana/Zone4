const fs = require('fs');
const raw = JSON.parse(fs.readFileSync('skins.json', 'utf-8'));

const skins = Object.entries(raw.items_list).map(([name, item]) => ({
  market_hash_name: name,
  icon_url: item.icon_url,
  type: item.type
}));

fs.writeFileSync('skins_ready.json', JSON.stringify(skins, null, 2));
console.log(`Готово! Сохранено ${skins.length} скинов в skins_ready.json`);
